package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.feedback.dto.req.FeedbackRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.feedback.dto.res.FeedbackResponse;
import com.example.ingle.domain.member.dto.res.MemberProfileImageResponse;
import com.example.ingle.domain.member.dto.res.MyPageResponse;
import com.example.ingle.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController implements MemberApiSpecification{

    private final MemberService memberService;

    // 마이페이지 조회
    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPage(@AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMyPage(memberDetail));
    }

    // 마이페이지 수정
    @PutMapping
    public ResponseEntity<MyPageResponse> updateMyPage(@AuthenticationPrincipal MemberDetail memberDetail,
                                                       @Valid @RequestBody MemberInfoRequest memberInfoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMyPage(memberDetail, memberInfoRequest));
    }

    // 프로필 이미지 리스트 조회
    @GetMapping("/profile-image")
    public ResponseEntity<List<MemberProfileImageResponse>> getAllProfileImage(@AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getAllProfileImage(memberDetail));
    }

    // 프로필 사진 수정
    @PutMapping("/profile-image")
    public ResponseEntity<MemberProfileImageResponse> updateProfileImage(@AuthenticationPrincipal MemberDetail memberDetail,
                                                                         @RequestParam String imageName) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateProfileImage(memberDetail, imageName));
    }

    // 피드백 전송
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponse> sendFeedback(@AuthenticationPrincipal MemberDetail memberDetail,
                                                         @Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.sendFeedback(memberDetail, request));
    }
}
