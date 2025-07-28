package com.example.ingle.global.jwt;

import com.example.ingle.domain.member.Member;
import com.example.ingle.domain.member.dto.res.LoginResponseDto;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    // JWT 클레임에서 권한 정보를 저장할 키
    private static final String AUTHORITIES_KEY = "auth";

    public static final long ACCESS_TOKEN_EXPIRED_TIME = 1 * 60 * 60 * 1000L;   // 1시간
    public static final long REFRESH_TOKEN_EXPIRED_TIME = 3 * 24 * 60 * 60 * 1000L; // 3일

    private final Key key;
    private final MemberDetailService memberDetailService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            MemberDetailService memberDetailService, RefreshTokenRepository refreshTokenRepository
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberDetailService = memberDetailService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // 인증 성공 시 토큰 생성
    public LoginResponseDto generateToken(Authentication authentication) {

        log.info("[JWT 발급 요청]");

        // 권한 정보를 문자열로 변환 ( ROLE_USER, ROLE_ADMIN,... )
        String authorities = extractAuthorities(authentication);

        // 토큰 만료 시간 계산
        long now = new Date().getTime();
        Date accessTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);
        Date refreshTime = new Date(now + REFRESH_TOKEN_EXPIRED_TIME);

        // 멤버 객체 꺼내기
        MemberDetail userDetails = memberDetailService.loadUserByUsername(authentication.getName());
        log.info("[JWT 발급] username: {}", userDetails.getUsername());
        Member member = userDetails.getMember();

        String accessToken = createToken(authentication.getName(), authorities, ACCESS_TOKEN_EXPIRED_TIME);
        String refreshToken = createToken(authentication.getName(), authorities, REFRESH_TOKEN_EXPIRED_TIME);

        log.info("[JWT 발급 완료] accessToken: {}, refreshToken: {}", accessToken, refreshToken);

        return buildLoginResponse(member, accessToken, refreshToken, accessTime, refreshTime);
    }

    // Member 객체만으로 JWT 발급
    public LoginResponseDto generateTokenFromMember(Member member) {

        log.info("[JWT 발급 요청]");

        String studentId = member.getStudentId();
        String authorities = "ROLE_USER";

        long now = new Date().getTime();
        Date accessTime = new Date(now + ACCESS_TOKEN_EXPIRED_TIME);
        Date refreshTime = new Date(now + REFRESH_TOKEN_EXPIRED_TIME);

        String accessToken = createToken(studentId, authorities, ACCESS_TOKEN_EXPIRED_TIME);
        String refreshToken = createToken(studentId, authorities, REFRESH_TOKEN_EXPIRED_TIME);

        log.info("[간단 JWT 발급] studentId: {}, accessToken: {}, refreshToken: {}", studentId, accessToken, refreshToken);

        return buildLoginResponse(member, accessToken, refreshToken, accessTime, refreshTime);
    }

    // 인증 및 토큰 생성
    public LoginResponseDto authenticateAndGenerateToken(AuthenticationManagerBuilder authenticationManagerBuilder,
                                                         String studentId, String password, Member member) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(studentId, password);

        log.debug("Spring Security 인증 시작");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) {
            log.error("[인증 실패] studentId = {}", studentId);
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        // 리프레시 토큰 존재 시 삭제
        if (refreshTokenRepository.findByStudentId(member.getStudentId()).isPresent()) {
            refreshTokenRepository.deleteByStudentId(member.getStudentId());
        }

        // JWT 토큰 생성 및 저장
        LoginResponseDto token = generateToken(authentication);
        RefreshToken refreshToken = RefreshToken.builder()
                .member(member)
                .refreshToken(token.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
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
    private String createToken(String subject, String authorities, long expiration) {

        log.info("[Access Token 생성]");
        return Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    private LoginResponseDto buildLoginResponse(Member member, String accessToken, String refreshToken,
                                                Date accessTime, Date refreshTime) {
        log.info("[Refresh Token 생성]");
        return LoginResponseDto.builder()
                .member(member)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresDate(accessTime)
                .refreshTokenExpiresDate(refreshTime)
                .build();
    }

    // JWT에서 username 추출
    public String parseUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}