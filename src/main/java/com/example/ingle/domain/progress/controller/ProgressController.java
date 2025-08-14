package com.example.ingle.domain.progress.controller;

import com.example.ingle.domain.progress.ProgressService;
import com.example.ingle.domain.progress.res.CompleteTutorialResponse;
import com.example.ingle.domain.progress.res.ProgressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-rewards")
public class ProgressController implements ProgressApiSpecification {

    private final ProgressService progressService;

    // 튜토리얼 완료 처리
    @PostMapping("/tutorials/{tutorialId}/complete")
    public ResponseEntity<CompleteTutorialResponse> completeTutorial(
            @RequestParam Long memberId,
            @PathVariable Long tutorialId) {

        CompleteTutorialResponse response = progressService.completeTutorial(memberId, tutorialId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자 진행률 조회
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ProgressResponse> getProgressByMemberId(@PathVariable Long memberId) {

        ProgressResponse response = progressService.getProgressByMemberId(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
