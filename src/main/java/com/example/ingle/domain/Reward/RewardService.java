package com.example.ingle.domain.reward;

import com.example.ingle.domain.reward.dto.res.RewardCardResponseDto;
import com.example.ingle.domain.reward.dto.res.RewardResponseDto;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    // 리워드 카드 조회
    @Transactional(readOnly = true)
    public RewardCardResponseDto getRewardCard(Integer position) {

        log.info("[리워드 카드 조회] position: {}", position);

        Reward reward = rewardRepository.findByPosition(position)
                .orElseThrow(() -> {
                    log.warn("[리워드 카드 조회 실패] 존재하지 않는 위치: {}", position);
                    return new CustomException(ErrorCode.REWARD_NOT_FOUND);
                });

        log.info("[리워드 카드 조회 성공]");

        return RewardCardResponseDto.builder().reward(reward).build();
    }

    // 특정 위치 리워드 조회
    @Transactional(readOnly = true)
    public RewardResponseDto getReward(Integer position) {

        log.info("[리워드 조회] position: {}", position);

        Reward reward = rewardRepository.findByPosition(position)
                .orElseThrow(() -> {
                    log.warn("[리워드 조회 실패] 존재하지 않는 위치: {}", position);
                    return new CustomException(ErrorCode.REWARD_NOT_FOUND);
                });

        log.info("[리워드 조회 성공]");

        return RewardResponseDto.builder().reward(reward).build();
    }

    // 리워드 목록 조회(위치순)
    @Transactional(readOnly = true)
    public List<RewardResponseDto> getAllRewards() {

        log.info("[전체 리워드 조회]");

        List<Reward> rewards = rewardRepository.findAllOrderByPosition();
        List<RewardResponseDto> rewardResponseDtos = rewards.stream()
                .map(reward -> RewardResponseDto.builder().reward(reward).build())
                .toList();

        log.info("[전체 리워드 조회 성공]");

        return rewardResponseDtos;
    }
}
