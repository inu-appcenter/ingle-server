package com.example.ingle.global.jwt;

import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.exception.ErrorResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // HTTP 헤더에서 JWT 토큰이 담기는 키 이름
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return Arrays.stream(excludedUris)
                .anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        try {
            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt, "access")) {

                Authentication authentication = jwtProvider.getAuthentication(jwt);

                // 밴된 사용자 체크
                if (authentication.getPrincipal() instanceof MemberDetail memberDetail) {
                    if (memberDetail.getMember().getRole() == Role.BANNED) {
                        log.warn("[JWT 검증 실패] 밴된 사용자: studentId={}, URI={}",
                                memberDetail.getUsername(), requestURI);
                        throw new CustomException(ErrorCode.MEMBER_BANNED);
                    }
                }

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("[JWT 검증 실패] 유효하지 않은 토큰: {}, URI: {}", jwt, requestURI);
                throw new CustomException(ErrorCode.JWT_NOT_VALID);
            }
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            log.error("[JWT 필터 오류] {}", e.getMessage());
            setErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponseEntity errorResponse = ErrorResponseEntity.toResponseEntity(errorCode).getBody();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(json);
    }

    private final String[] excludedUris = {
            "/api/v1/auth/signup",
            "/api/v1/auth/login",
            "/api/v1/auth/login/test",
            "/api/v1/auth/refresh",
            "/api/v1/auth/nickname/**",
            "/api/v1/images/**",
            "/images/**",
            "/favicon.ico",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
