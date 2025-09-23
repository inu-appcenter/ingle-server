package com.example.ingle.domain.member.service;

import com.example.ingle.domain.feedback.repository.FeedbackRepository;
import com.example.ingle.domain.image.repository.ImageRepository;
import com.example.ingle.domain.feedback.domain.Feedback;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.feedback.dto.req.FeedbackRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.feedback.dto.res.FeedbackResponse;
import com.example.ingle.domain.member.dto.res.MemberProfileImageResponse;
import com.example.ingle.domain.member.dto.res.MyPageResponse;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ImageRepository imageRepository;
    private final FeedbackRepository feedbackRepository;
    private final MemberQueryService memberQueryService;

    @Transactional(readOnly = true)
    public MyPageResponse getMyPage(MemberDetail memberDetail) {
        Member member = memberQueryService.getMemberByStudentId(memberDetail.getUsername());

        return MyPageResponse.from(member);
    }

    @Transactional
    public MyPageResponse updateMyPage(MemberDetail memberDetail, MemberInfoRequest memberInfoRequest) {
        Member member = memberQueryService.getMemberByStudentId(memberDetail.getUsername());

        member.updateMember(memberInfoRequest);

        return MyPageResponse.from(member);
    }

    @Transactional
    public MemberProfileImageResponse updateProfileImage(MemberDetail memberDetail, String imageName) {
        Member member = memberQueryService.getMemberByStudentId(memberDetail.getUsername());

        MemberProfileImageResponse memberProfileImageResponse = getProfileImage(imageName);

        member.updateProfileImage(memberProfileImageResponse.imageUrl());

        return memberProfileImageResponse;
    }

    @Transactional
    public FeedbackResponse sendFeedback(MemberDetail memberDetail, FeedbackRequest request) {
        Member member = memberQueryService.getMemberByStudentId(memberDetail.getUsername());
        Feedback feedback = feedbackRepository.save(Feedback.of(member.getId(), request.content()));

        return FeedbackResponse.from(feedback.getMemberId(), feedback.getContent());
    }

    private MemberProfileImageResponse getProfileImage(String imageName) {
        return imageRepository.findByName(imageName)
                .orElseThrow(() -> {
                    log.warn("[프로필 사진 변경 실패] 이미지 없음: imageName={}", imageName);
                    return new CustomException(ErrorCode.IMAGE_NOT_FOUND);
                });
    }
}
