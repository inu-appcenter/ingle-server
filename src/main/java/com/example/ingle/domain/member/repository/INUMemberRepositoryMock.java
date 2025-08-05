package com.example.ingle.domain.member.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@ConditionalOnProperty(name = "oracle.enabled", havingValue = "false", matchIfMissing = true) // 설정 없어도 false로 처리, 즉 기본적으로 Mock 사용
public class INUMemberRepositoryMock implements INUMemberRepository {

    @Override
    public boolean verifySchoolLogin(String studentId, String password) {
        log.info("[로컬 테스트 로그인] 항상 성공 처리");
        return true;
    }
}