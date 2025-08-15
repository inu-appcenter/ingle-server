package com.example.ingle.domain.stamp;

import com.example.ingle.domain.stamp.res.CompleteTutorialResponse;
import com.example.ingle.domain.stamp.res.ProgressResponse;
import com.example.ingle.domain.stamp.res.StampResponse;
import com.example.ingle.domain.tutorial.Tutorial;
import com.example.ingle.domain.tutorial.TutorialRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final TutorialRepository tutorialRepository;

    //  특정 회원의 스탬프 상세 조회 (기존 기본조회 + 상세조회 통합)
    @Transactional(readOnly = true)
    public StampResponse getStamp(Long memberId, Long stampId) {

        Stamp stamp = stampRepository.findByIdAndMemberId(stampId, memberId)
                .orElseThrow(() -> {
                    log.warn("[스탬프 조회 실패] 존재하지 않는 스탬프: memberId={}, stampId={}", memberId, stampId);
                    return new CustomException(ErrorCode.STAMP_NOT_FOUND);
                });

        return StampResponse.builder().stamp(stamp).build();
    }

    // 특정 회원의 전체 스탬프 목록 조회
    @Transactional(readOnly = true)
    public List<StampResponse> getAllStamps(Long memberId) {
        return stampRepository.findAllByMemberIdOrderById(memberId).stream()
                .map(stamp -> StampResponse.builder().stamp(stamp).build())
                .toList();
    }
    // 튜토리얼 완료 처리
    @Transactional
    public CompleteTutorialResponse completeTutorial(Long memberId, Long tutorialId) {

        Stamp stamp = stampRepository.findByMemberIdAndTutorialId(memberId, tutorialId)
                .orElseThrow(() -> {
                    log.warn("[튜토리얼 완료 실패] 존재하지 않는 튜토리얼 또는 스탬프: memberId={}, tutorialId={}", memberId, tutorialId);
                    throw new CustomException(ErrorCode.TUTORIAL_NOT_FOUND);
                });

        if (stamp.getCompletedAt() != null) {
            log.warn("[튜토리얼 완료 실패] 이미 완료된 튜토리얼: memberId={}, tutorialId={}", memberId, tutorialId);
            throw new CustomException(ErrorCode.TUTORIAL_ALREADY_COMPLETED);
        }

        stamp.complete(); // completedAt 업데이트
        return CompleteTutorialResponse.builder()
                .stamp(stamp)
                .build();
    }

    // 진행률 조회
    @Transactional(readOnly = true)
    public ProgressResponse getProgressByMemberId(Long memberId) {
        // 완료된 튜토리얼(완료 시간이 null이 아닌 스탬프) 수
        Long completedCount = stampRepository.countCompletedTutorialsByMemberId(memberId);

        // 전체 튜토리얼 수
        Long totalCount = tutorialRepository.count();

        return ProgressResponse.builder()
                .completedCount(completedCount.intValue())
                .totalCount(totalCount.intValue())
                .build();
    }
}
