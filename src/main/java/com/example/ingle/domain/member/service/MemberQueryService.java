package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    protected Member getMemberByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    log.warn("[회원 정보 조회 실패] 사용자 없음: studentId={}", studentId);
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }
}
