package com.example.ingle.domain.stamp.controller;

import com.example.ingle.domain.stamp.service.StampService;
import com.example.ingle.domain.stamp.dto.res.CompleteTutorialResponse;
import com.example.ingle.domain.stamp.dto.res.StampResponse;
import com.example.ingle.domain.member.domain.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stamps")
@Validated
public class StampController implements StampApiSpecification {

    private final StampService stampService;

    // 로그인한 사용자의 전체 스탬프 목록 조회
    @GetMapping
    public ResponseEntity<List<StampResponse>> getAllStamps(
            @AuthenticationPrincipal MemberDetail memberDetail) {
        Long memberId = memberDetail.getMember().getId();
        return ResponseEntity.ok(stampService.getAllStamps(memberId));
    }

    // 로그인한 사용자의 튜토리얼 완료 처리
    @PostMapping("/tutorials/{tutorialId}/complete")
    public ResponseEntity<CompleteTutorialResponse> completeTutorial(
            @PathVariable Long tutorialId,
            @AuthenticationPrincipal MemberDetail memberDetail) {
        Long memberId = memberDetail.getMember().getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(stampService.completeTutorial(memberId, tutorialId));
    }
}