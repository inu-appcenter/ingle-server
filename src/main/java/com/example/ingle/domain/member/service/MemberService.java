package com.example.ingle.domain.member.service;

import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.domain.image.repository.ImageRepository;
import com.example.ingle.domain.image.service.ImageService;
import com.example.ingle.domain.member.domain.Feedback;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.member.dto.req.FeedbackRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.FeedbackResponse;
import com.example.ingle.domain.member.dto.res.MemberProfileImageResponse;
import com.example.ingle.domain.member.dto.res.MyPageResponse;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.domain.stamp.entity.Stamp;
import com.example.ingle.domain.stamp.repository.StampRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

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

    @Transactional
    public MemberProfileImageResponse updateProfileImage(MemberDetail memberDetail, String imageName) {
        Member member = getMemberByStudentId(memberDetail.getUsername());

        MemberProfileImageResponse memberProfileImageResponse = getProfileImage(imageName);

        member.updateProfileImage(memberProfileImageResponse.imageUrl());

        return memberProfileImageResponse;
    }

    @Transactional
    public FeedbackResponse sendFeedback(MemberDetail memberDetail, FeedbackRequest request) {
        Member member = getMemberByStudentId(memberDetail.getUsername());
        Feedback feedback = Feedback.builder().memberId(member.getId()).content(request.content()).build();

        return FeedbackResponse.from(feedback.getMemberId(), feedback.getContent());
    }

    protected Member getMemberByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> {
                    log.warn("[회원 정보 조회 실패] 사용자 없음: studentId={}", studentId);
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }

    private MemberProfileImageResponse getProfileImage(String imageName) {
        return imageRepository.findByName(imageName)
                .orElseThrow(() -> {
                    log.warn("[프로필 사진 변경 실패] 이미지 없음: imageName={}", imageName);
                    return new CustomException(ErrorCode.IMAGE_NOT_FOUND);
                });
    }
}
