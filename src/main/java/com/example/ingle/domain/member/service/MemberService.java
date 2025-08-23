package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.MyPageResponse;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.domain.member.domain.MemberDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MyPageResponse getMyPage(MemberDetail memberDetail) {
        Member member = getMemberByStudentId(memberDetail.getUsername());

        return MyPageResponse.from(member);
    }

    @Transactional
    public MyPageResponse updateMyPage(MemberDetail memberDetail, MemberInfoRequest memberInfoRequest) {
        Member member = getMemberByStudentId(memberDetail.getUsername());

        member.updateMember(memberInfoRequest);

        return MyPageResponse.from(member);
    }

    protected Member getMemberByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    log.warn("[회원 정보 조회 실패] 사용자 없음: studentId={}", studentId);
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }
}
