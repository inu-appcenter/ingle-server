package com.example.ingle.global.config;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.domain.MemberDetail;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingConfig {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void loggingPointcut() {}

    @Around("loggingPointcut()")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String memberId = getCurrentMemberId(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("user={} {} {} {}ms", memberId, method, uri, duration);
        }
    }

    private String getCurrentMemberId(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() instanceof String) {
            return request.getHeader("X-Forwarded-For");
        } else {
            if(authentication.getPrincipal() instanceof MemberDetail memberDetail) {
                Member member = memberDetail.getMember();
                return member != null ? String.valueOf(member.getId()) : "unknown";
            }
        }
        return "anonymous";
    }
}