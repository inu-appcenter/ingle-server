package com.example.ingle.domain.reward.controller;

import com.example.ingle.domain.reward.RewardService;
import com.example.ingle.domain.reward.dto.res.RewardCardResponseDto;
import com.example.ingle.domain.reward.dto.res.RewardResponseDto;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewards")
@Validated
public class RewardController implements RewardApiSpecification {

    private final RewardService rewardService;

    // 리워드 조회
    @GetMapping("/{position}")
    public ResponseEntity<RewardResponseDto> getReward(
            @PathVariable @Min(1) Integer position) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.getReward(position));
    }

    // 리워드 카드 조회
    @GetMapping("/{position}/card")
    public ResponseEntity<RewardCardResponseDto> getRewardCard(
            @PathVariable("position") @Min(1) Integer position) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.getRewardCard(position));
    }

    // 전체 리워드 목록 조회
    @GetMapping
    public ResponseEntity<List<RewardResponseDto>> getAllRewards() {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.getAllRewards());
    }
}