package com.example.ingle.domain.stamp;

import com.example.ingle.domain.stamp.entity.MemberStamp;
import com.example.ingle.domain.stamp.entity.Stamp;
import com.example.ingle.domain.stamp.repository.MemberStampRepository;
import com.example.ingle.domain.stamp.repository.StampRepository;
import com.example.ingle.domain.stamp.res.CompleteTutorialResponse;
import com.example.ingle.domain.stamp.res.ProgressResponse;
import com.example.ingle.domain.stamp.res.StampResponse;
import com.example.ingle.domain.tutorial.TutorialRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final MemberStampRepository memberStampRepository;
    private final TutorialRepository tutorialRepository;

    //  특정 회원의 스탬프 상세 조회 (기존 기본조회 + 상세조회 통합)
    @Transactional(readOnly = true)
    public StampResponse getStamp(Long memberId, Long stampId) {

        Stamp stamp = stampRepository.findById(stampId)
                .orElseThrow(() -> {
                    log.warn("[스탬프 조회 실패] 존재하지 않는 스탬프: stampId={}, memberId={}", stampId, memberId);
                    return new CustomException(ErrorCode.STAMP_NOT_FOUND);
                });

        // 해당 회원의 스탬프 획득 정보 조회
        MemberStamp memberStamp = memberStampRepository
                .findByMemberIdAndTutorialId(memberId, stamp.getTutorialId())
                .orElse(null);

        return StampResponse.builder()
                .stamp(stamp)
                .completedAt(memberStamp != null ? memberStamp.getCompletedAt() : null)
                .build();
    }

    // 특정 회원의 전체 스탬프 목록 조회
    @Transactional(readOnly = true)
    public List<StampResponse> getAllStamps(Long memberId) {

        // 모든 스탬프 정보 조회
        List<Stamp> stamps = stampRepository.findAllByOrderById();

         // 해당 회원이 획득한 스탬프들을 Map으로 변환 (tutorialId -> MemberStamp)
        Map<Long, MemberStamp> memberStampMap = memberStampRepository
                .findAllByMemberId(memberId)
                .stream()
                .collect(Collectors.toMap(
                        MemberStamp::getTutorialId,
                        memberStamp -> memberStamp
                ));

        // 스탬프 정보와 획득 정보를 합쳐서 반환
        return stamps.stream()
                .map(stamp -> {
                    MemberStamp memberStamp = memberStampMap.get(stamp.getTutorialId());
                    return StampResponse.builder()
                            .stamp(stamp)
                            .completedAt(memberStamp != null ? memberStamp.getCompletedAt() : null)
                            .build();
                })
                .toList();
    }
    // 튜토리얼 완료 처리
    @Transactional
    public CompleteTutorialResponse completeTutorial(Long memberId, Long tutorialId) {

        // 스탬프 정보 확인
        Stamp stamp = stampRepository.findByTutorialId(tutorialId)
                .orElseThrow(() -> {
                    log.warn("[튜토리얼 완료 실패] 존재하지 않는 튜토리얼 또는 스탬프: tutorialId={}, memberId={}", tutorialId, memberId);
                    throw new CustomException(ErrorCode.TUTORIAL_NOT_FOUND);
                });

        // 이미 획득했는지 확인
        if (memberStampRepository.existsByMemberIdAndTutorialId(memberId, tutorialId)) {
            log.warn("[튜토리얼 완료 실패] 이미 완료된 튜토리얼: tutorialId={}, memberId={}", tutorialId, memberId);
            throw new CustomException(ErrorCode.TUTORIAL_ALREADY_COMPLETED);
        }

        // 스탬프 획득 정보 저장
        MemberStamp memberStamp = MemberStamp.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .build();

        memberStampRepository.save(memberStamp);

        return CompleteTutorialResponse.builder()
                .id(stamp.getId())
                .tutorialId(tutorialId)
                .completedAt(memberStamp.getCompletedAt())
                .build();
    }

    // 진행률 조회
    @Transactional(readOnly = true)
    public ProgressResponse getProgressByMemberId(Long memberId) {

        // 완료된 튜토리얼 수 (획득한 스탬프 수)
        Long completedCount = memberStampRepository.countCompletedByMemberId(memberId);

        // 전체 튜토리얼 수
        Long totalCount = tutorialRepository.count();

        return ProgressResponse.builder()
                .completedCount(completedCount.intValue())
                .totalCount(totalCount.intValue())
                .build();
    }
}
