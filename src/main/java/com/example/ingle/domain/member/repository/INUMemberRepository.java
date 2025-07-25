package com.example.ingle.domain.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class INUMemberRepository {

    @Qualifier("oracleJdbc")
    private final JdbcTemplate jdbcTemplate;

    public INUMemberRepository(@Qualifier("oracleJdbc") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean verifySchoolLogin(String username, String password) {

        log.info("[학교 로그인 검증] username: {}", username);

        String sql = "SELECT F_LOGIN_CHECK(?,?) FROM DUAL";

        try {
            String result = jdbcTemplate.queryForObject(sql, String.class, username, password);
            log.info("[학교 로그인 검증 결과] result: {}", result);
            return "Y".equals(result);
        } catch (Exception e) {
            log.error("[학교 로그인 검증 실패] username: {}, error: {}", username, e.getMessage());
            return false;
        }
    }
}
