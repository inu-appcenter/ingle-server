package com.example.ingle.domain.memberreward;

import com.example.ingle.domain.memberreward.dto.res.CompleteTutorialResponseDto;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardProgressResponseDto;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardStatusResponseDto;
import com.example.ingle.domain.tutorial.Tutorial;
import com.example.ingle.domain.tutorial.TutorialRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRewardService {

    private final MemberRewardRepository memberRewardRepository;
    private final TutorialRepository tutorialRepository;

    // 튜토리얼 완료 처리
    @Transactional
    public CompleteTutorialResponseDto completeTutorial(Long memberId, Long tutorialId) {

        log.info("[튜토리얼 완료 처리] memberId: {}, tutorialId: {}", memberId, tutorialId);

        if (memberRewardRepository.existsByMemberIdAndTutorialId(memberId, tutorialId)) {
            log.warn("[튜토리얼 완료 실패] 이미 완료된 튜토리얼: memberId: {}, tutorialId: {}",
                    memberId, tutorialId);
            throw new CustomException(ErrorCode.TUTORIAL_ALREADY_COMPLETED);
        }

        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> {
                    log.warn("[튜토리얼 완료 실패] 존재하지 않는 튜토리얼: tutorialId: {}", tutorialId);
                    return new CustomException(ErrorCode.TUTORIAL_NOT_FOUND);
                });

        Integer rewardPosition = tutorial.getRewardPosition();

        MemberReward memberReward = MemberReward.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .rewardPosition(rewardPosition)
                .isCompleted(true)
                .completedAt(LocalDateTime.now())
                .build();

        memberRewardRepository.save(memberReward);

        log.info("[튜토리얼 완료 성공] memberRewardId: {}, rewardPosition: {}",
                memberReward.getId(), rewardPosition);

        return CompleteTutorialResponseDto.builder()
                .memberReward(memberReward)
                .build();
    }

    // 특정 리워드 획득 여부 조회
    @Transactional(readOnly = true)
    public MemberRewardStatusResponseDto getRewardStatusByPosition(Long memberId, Integer rewardPosition) {

        log.info("[리워드 상태 조회] memberId: {}, rewardPosition: {}", memberId, rewardPosition);

        Optional<MemberReward> memberRewardOpt = memberRewardRepository
                .findByMemberIdAndRewardPosition(memberId, rewardPosition);

        if (memberRewardOpt.isEmpty()) {
            log.info("[리워드 상태 조회 결과] 미완료: memberId: {}, rewardPosition: {}", memberId, rewardPosition);

            // 해당 리워드 위치에 해당하는 튜토리얼이 존재하는지 확인
            Tutorial tutorial = tutorialRepository.findByRewardPosition(rewardPosition)
                    .orElseThrow(() -> {
                        log.warn("[리워드 상태 조회 실패] 존재하지 않는 리워드 위치: rewardPosition: {}", rewardPosition);
                        return new CustomException(ErrorCode.REWARD_NOT_FOUND);
                    });

            return MemberRewardStatusResponseDto.notCompleted(tutorial.getId(), rewardPosition);
        }

        MemberReward memberReward = memberRewardOpt.get();
        log.info("[리워드 상태 조회 결과] 완료: memberId: {}, rewardPosition: {}, completedAt: {}",
                memberId, rewardPosition, memberReward.getCompletedAt());

        return MemberRewardStatusResponseDto.fromMemberReward(memberReward);
    }

    // 진행률 조회
    @Transactional(readOnly = true)
    public MemberRewardProgressResponseDto getProgressByMemberId(Long memberId) {
        // 완료된 튜토리얼(획득한 리워드) 수
        Long completedCount = memberRewardRepository.countCompletedTutorialsByMemberId(memberId);

        // 전체 튜토리얼 수
        Long totalCount = tutorialRepository.count();

        return MemberRewardProgressResponseDto.builder()
                .completedCount(completedCount.intValue())
                .totalCount(totalCount.intValue())
                .build();
    }
}
