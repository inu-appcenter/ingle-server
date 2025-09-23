package com.example.ingle.global.jwt;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.service.MemberDetailService;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.refreshToken.RefreshToken;
import com.example.ingle.global.jwt.refreshToken.RefreshTokenRepository;
import com.example.ingle.global.utils.CookieUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth";

    public static final long ACCESS_TOKEN_EXPIRED_TIME = 3 * 60 * 60 * 1000L;   // 3시간
    public static final long REFRESH_TOKEN_EXPIRED_TIME = 3 * 24 * 60 * 60 * 1000L; // 3일

    private final Key key;
    private final MemberDetailService memberDetailService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            MemberDetailService memberDetailService, RefreshTokenRepository refreshTokenRepository
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberDetailService = memberDetailService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginSuccessResponse generateToken(Authentication authentication, HttpServletResponse response) {
        String authorities = extractAuthorities(authentication);
        Date accessTime = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRED_TIME);

        MemberDetail userDetails = memberDetailService.loadUserByUsername(authentication.getName());
        Member member = userDetails.getMember();

        String accessToken = createAccessToken(authentication.getName(), authorities);
        String refreshToken = createRefreshToken();

        CookieUtil.setCookie(response, "refreshToken", refreshToken, (int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));

        refreshTokenRepository.save(RefreshToken.builder().member(member).refreshToken(refreshToken).build());

        return LoginSuccessResponse.from(member, accessToken, accessTime);
    }

    public LoginSuccessResponse generateTokenFromMember(Member member, HttpServletResponse response) {
        String studentId = member.getStudentId();
        String authorities = "ROLE_USER";
        Date accessTime = new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRED_TIME);

        String accessToken = createAccessToken(studentId, authorities);
        String refreshToken = createRefreshToken();

        CookieUtil.setCookie(response, "refreshToken", refreshToken, (int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));

        refreshTokenRepository.save(RefreshToken.builder().member(member).refreshToken(refreshToken).build());

        return LoginSuccessResponse.from(member, accessToken, accessTime);
    }

    public LoginSuccessResponse authenticateAndGenerateToken(Member member, HttpServletResponse response) {
        MemberDetail memberDetail = new MemberDetail(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                memberDetail, null, memberDetail.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (refreshTokenRepository.findByStudentId(member.getStudentId()).isPresent()) {
            refreshTokenRepository.deleteByStudentId(member.getStudentId());
        }

        return generateToken(authentication, response);
    }

    public boolean validateToken(String token, String tokenType) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new CustomException(ErrorCode.JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            throw new CustomException(ErrorCode.JWT_MALFORMED);
        } catch (ExpiredJwtException e) {
            if ("access".equals(tokenType)) {
                throw new CustomException(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            } else if ("refresh".equals(tokenType)) {
                throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
            } else {
                throw new CustomException(ErrorCode.JWT_NOT_VALID);
            }
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.JWT_NOT_VALID);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        MemberDetail userDetails = memberDetailService.loadUserByUsername(claims.getSubject());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    private String extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private String createAccessToken(String subject, String authorities) {
        return Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + JwtProvider.ACCESS_TOKEN_EXPIRED_TIME))
                .compact();
    }

    private String createRefreshToken() {
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + JwtProvider.REFRESH_TOKEN_EXPIRED_TIME))
                .compact();
    }

    public void validateRefreshTokenAndDelete(String refreshToken) {
        if (!validateToken(refreshToken, "refresh")) {
            log.warn("[JWT 토큰 유효성 검증 실패] 만료된 Refresh Token");
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
            throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    public RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> {
                    log.warn("[RefreshToken 조회 실패] 저장된 토큰 없음");
                    return new CustomException(ErrorCode.JWT_NOT_FOUND);
                });
    }

    public RefreshToken findRefreshTokenByStudentId(String studentId) {
        return refreshTokenRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    log.warn("[RefreshToken 조회 실패] 저장된 토큰 없음");
                    return new CustomException(ErrorCode.JWT_NOT_FOUND);
                });
    }

    public void matchRefreshToken(String studentRefreshToken, String refreshToken) {
        if (!studentRefreshToken.equals(refreshToken)) {
            log.warn("[RefreshToken 불일치] 요청 토큰과 저장 토큰이 다름");
            throw new CustomException(ErrorCode.JWT_NOT_MATCH);
        }
    }

    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public void deleteRefreshTokenByStudentId(String studentId) {
        refreshTokenRepository.deleteByStudentId(studentId);
    }
}