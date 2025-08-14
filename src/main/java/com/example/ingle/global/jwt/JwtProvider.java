package com.example.ingle.global.jwt;

import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
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

    // JWT 클레임에서 권한 정보를 저장할 키
    private static final String AUTHORITIES_KEY = "auth";

    public static final long ACCESS_TOKEN_EXPIRED_TIME = 1 * 60 * 60 * 1000L;   // 1시간
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

    // 인증 성공 시 토큰 생성
    public LoginSuccessResponse generateToken(Authentication authentication, HttpServletResponse response) {

        log.info("[JWT 발급 요청] From Authentication");

        // 권한 정보를 문자열로 변환 ( ROLE_USER, ROLE_ADMIN,... )
        String authorities = extractAuthorities(authentication);

        // 토큰 만료 시간 계산
        long now = new Date().getTime();
        Date accessTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);

        // 멤버 객체 꺼내기
        MemberDetail userDetails = memberDetailService.loadUserByUsername(authentication.getName());
        Member member = userDetails.getMember();

        String accessToken = createAccessToken(authentication.getName(), authorities);
        String refreshToken = createRefreshToken();

        CookieUtil.setCookie(response, "refreshToken", refreshToken, (int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));

        refreshTokenRepository.save(RefreshToken.builder().member(member).refreshToken(refreshToken).build());

        log.info("[Authentication JWT 발급 완료] accessToken: {}, refreshToken: {}", accessToken, refreshToken);

        return LoginSuccessResponse.from(member, accessToken, accessTime);
    }

    // Member 객체만으로 JWT 발급
    public LoginSuccessResponse generateTokenFromMember(Member member, HttpServletResponse response) {

        log.info("[JWT 발급 요청] From Member");

        String studentId = member.getStudentId();
        String authorities = "ROLE_USER";

        long now = new Date().getTime();
        Date accessTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);

        String accessToken = createAccessToken(studentId, authorities);
        String refreshToken = createRefreshToken();

        CookieUtil.setCookie(response, "refreshToken", refreshToken, (int) (REFRESH_TOKEN_EXPIRED_TIME / 1000));

        refreshTokenRepository.save(RefreshToken.builder().member(member).refreshToken(refreshToken).build());

        log.info("[Member JWT 발급] studentId: {}, accessToken: {}, refreshToken: {}", studentId, accessToken, refreshToken);

        return LoginSuccessResponse.from(member, accessToken, accessTime);
    }

    // 포털 로그인 토큰 생성
    public LoginSuccessResponse authenticateAndGenerateToken(String studentId, Member member, HttpServletResponse response) {
        log.info("INU 포털 로그인 이미 성공 → Spring Security 인증 절차 스킵");

        MemberDetail memberDetail = new MemberDetail(member);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                memberDetail, null, memberDetail.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 리프레시 토큰 존재 시 삭제
        if (refreshTokenRepository.findByStudentId(member.getStudentId()).isPresent()) {
            refreshTokenRepository.deleteByStudentId(member.getStudentId());
        }

        // JWT 토큰 생성 및 저장
        LoginSuccessResponse token = generateToken(authentication, response);

        log.info("[JWT 발급 완료] studentId = {}", studentId);

        return token;
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token, String tokenType) {

        log.info("[JWT 유효성 검증] tokenType: {}", tokenType);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            // 서명 불일치
            throw new CustomException(ErrorCode.JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            // 구조 문제
            throw new CustomException(ErrorCode.JWT_MALFORMED);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
            if ("access".equals(tokenType)) {
                throw new CustomException(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            } else if ("refresh".equals(tokenType)) {
                throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
            } else {
                throw new CustomException(ErrorCode.JWT_NOT_VALID);
            }
        } catch (UnsupportedJwtException e) {
            // 지원하지 않는 형식
            throw new CustomException(ErrorCode.JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            // 잘못된 인자
            throw new CustomException(ErrorCode.JWT_NOT_VALID);
        }
    }

    // 토큰으로부터 인증 정보 조회
    public Authentication getAuthentication(String token) {

        log.info("[JWT 인증 정보 조회]");
        // 토큰 복호화 및 클레임 추출
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 사용자 정보 조회
        MemberDetail userDetails = memberDetailService.loadUserByUsername(claims.getSubject());

        // 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // 인증 객체 생성
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    private String extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // 토큰 생성
    private String createAccessToken(String subject, String authorities) {

        log.info("[Access Token 생성]");
        return Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + JwtProvider.ACCESS_TOKEN_EXPIRED_TIME))
                .compact();
    }

    private String createRefreshToken() {

        log.info("[Refresh Token 생성]");
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + JwtProvider.REFRESH_TOKEN_EXPIRED_TIME))
                .compact();
    }
}