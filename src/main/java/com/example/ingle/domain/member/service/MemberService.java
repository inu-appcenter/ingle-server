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
        Member member = memberRepository.findById(memberDetail.getMember().getId())
                .orElseThrow(() -> {
                    log.warn("[마이페이지 조회 실패] 회원 ID: {}", memberDetail.getMember().getId());
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });

        return MyPageResponse.from(member);
    }

    @Transactional
    public MyPageResponse updateMyPage(MemberDetail memberDetail, MemberInfoRequest memberInfoRequest) {
        Member member = memberRepository.findById(memberDetail.getMember().getId())
                .orElseThrow(() -> {
                    log.warn("[마이페이지 수정 실패] 회원 ID: {}", memberDetail.getMember().getId());
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });

        member.updateMember(memberInfoRequest);

        return MyPageResponse.from(member);
    }
}
