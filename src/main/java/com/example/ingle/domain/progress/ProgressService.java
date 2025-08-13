package com.example.ingle.domain.progress;

import com.example.ingle.domain.progress.res.CompleteTutorialResponse;
import com.example.ingle.domain.progress.res.ProgressResponse;
import com.example.ingle.domain.stamp.StampRepository;
import com.example.ingle.domain.tutorial.Tutorial;
import com.example.ingle.domain.tutorial.TutorialRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressService {

    private final StampRepository stampRepository;
    private final TutorialRepository tutorialRepository;
    private final ProgressRepository progressRepository;

    // 튜토리얼 완료 처리
    @Transactional
    public CompleteTutorialResponse completeTutorial(Long memberId, Long tutorialId) {

        // 튜토리얼 존재 여부 확인
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> {
                    log.warn("[튜토리얼 완료 실패] 존재하지 않는 튜토리얼: {}", tutorialId);
                    return new CustomException(ErrorCode.TUTORIAL_NOT_FOUND);
                });

        // 이미 완료된 튜토리얼인지 확인
        if (progressRepository.existsByMemberIdAndTutorialId(memberId, tutorialId)) {
            log.warn("[튜토리얼 완료 실패] 이미 완료된 튜토리얼: memberId={}, tutorialId={}", memberId, tutorialId);
            throw new CustomException(ErrorCode.TUTORIAL_ALREADY_COMPLETED);
        }

        // 진행상황 저장
        Progress progress = Progress.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .stampId(tutorial.getStampId())
                .isCompleted(true)
                .completedAt(LocalDateTime.now())
                .build();

        progressRepository.save(progress);

        return CompleteTutorialResponse.builder()
                .progress(progress)
                .build();
    }

    // 진행률 조회
    @Transactional(readOnly = true)
    public ProgressResponse getProgressByMemberId(Long memberId) {
        // 완료된 튜토리얼(획득한 스탬프) 수
        Long completedCount = progressRepository.countCompletedTutorialsByMemberId(memberId);

        // 전체 튜토리얼 수
        Long totalCount = tutorialRepository.count();

        return ProgressResponse.builder()
                .completedCount(completedCount.intValue())
                .totalCount(totalCount.intValue())
                .build();
    }
}
