package com.example.ingle.domain.memberreward.controller;

import com.example.ingle.domain.memberreward.MemberRewardService;
import com.example.ingle.domain.memberreward.dto.res.CompleteTutorialResponse;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardProgressResponse;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardStatusResponse;
import com.example.ingle.global.jwt.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-rewards")
public class MemberRewardController implements MemberRewardApiSpecification {

    private final MemberRewardService memberRewardService;

    // 튜토리얼 완료 처리
    @PostMapping("/{tutorialId}")
    public ResponseEntity<CompleteTutorialResponse> completeTutorial(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable("tutorialId") Long tutorialId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                memberRewardService.completeTutorial(memberDetail.getMember().getId(), tutorialId));
    }

    // 특정 리워드 상태 조회
    @GetMapping("/{position}")
    public ResponseEntity<MemberRewardStatusResponse> getRewardStatusByPosition(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable("position") Integer rewardPosition) {
        return ResponseEntity.status(HttpStatus.OK).body(
                memberRewardService.getRewardStatusByPosition(memberDetail.getMember().getId(), rewardPosition));
    }

    // 멤버 튜토리얼 진행률 조회
    @GetMapping("/progress")
    public ResponseEntity<MemberRewardProgressResponse> getProgress(
            @AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.status(HttpStatus.OK).body(
                memberRewardService.getProgressByMemberId(memberDetail.getMember().getId()));
    }
}
