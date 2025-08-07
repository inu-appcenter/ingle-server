package com.example.ingle.domain.reward;

import com.example.ingle.domain.reward.dto.res.RewardCardResponse;
import com.example.ingle.domain.reward.dto.res.RewardResponse;
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
    public RewardCardResponse getRewardCard(Integer position) {

        Reward reward = rewardRepository.findByPosition(position)
                .orElseThrow(() -> {
                    log.warn("[리워드 카드 조회 실패] 존재하지 않는 위치: {}", position);
                    return new CustomException(ErrorCode.REWARD_NOT_FOUND);
                });

        return RewardCardResponse.builder().reward(reward).build();
    }

    // 특정 위치 리워드 조회
    @Transactional(readOnly = true)
    public RewardResponse getReward(Integer position) {

        Reward reward = rewardRepository.findByPosition(position)
                .orElseThrow(() -> {
                    log.warn("[리워드 조회 실패] 존재하지 않는 위치: {}", position);
                    return new CustomException(ErrorCode.REWARD_NOT_FOUND);
                });

        return RewardResponse.builder().reward(reward).build();
    }

    // 리워드 목록 조회(위치순)
    @Transactional(readOnly = true)
    public List<RewardResponse> getAllRewards() {

        List<Reward> rewards = rewardRepository.findAllOrderByPosition();
        List<RewardResponse> rewardResponseDtos = rewards.stream()
                .map(reward -> RewardResponse.builder().reward(reward).build())
                .toList();

        return rewardResponseDtos;
    }
}
