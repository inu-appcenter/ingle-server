package com.example.ingle.domain.stamp;

import com.example.ingle.domain.stamp.dto.res.CompleteTutorialResponse;
import com.example.ingle.domain.stamp.dto.res.StampResponse;
import com.example.ingle.domain.stamp.entity.MemberStamp;
import com.example.ingle.domain.stamp.entity.Stamp;
import com.example.ingle.domain.stamp.repository.MemberStampRepository;
import com.example.ingle.domain.stamp.repository.StampRepository;
import com.example.ingle.domain.tutorial.TutorialRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final MemberStampRepository memberStampRepository;
    private final TutorialRepository tutorialRepository;

    /**
     * 특정 회원의 전체 스탬프 목록 조회
     * 튜토리얼 ID를 set으로 조회 후 스탬프 정보와 완료 여부 합쳐서 반환
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public List<StampResponse> getAllStamps(Long memberId) {

        List<Stamp> stamps = stampRepository.findAllByOrderById();

        Set<Long> completedTutorialIds = memberStampRepository
                .findAllByMemberId(memberId)
                .stream()
                .map(MemberStamp::getTutorialId) //tutorialId만 추출
                .collect(Collectors.toSet());

        return stamps.stream()
                .map(stamp -> StampResponse.from(
                        stamp,
                        completedTutorialIds.contains(stamp.getTutorialId())
                ))
                .toList();
    }

    /**
     * 튜토리얼 완료 처리 및 스탬프 획득
     * @param memberId
     * @param tutorialId
     * @return
     */
    @Transactional
    public CompleteTutorialResponse completeTutorial(Long memberId, Long tutorialId) {

        Stamp stamp = stampRepository.findByTutorialId(tutorialId)
                .orElseThrow(() -> {
                    log.warn("[튜토리얼 완료 실패] 존재하지 않는 튜토리얼 또는 스탬프: tutorialId={}, memberId={}", tutorialId, memberId);
                    throw new CustomException(ErrorCode.TUTORIAL_NOT_FOUND);
                });

        if (memberStampRepository.existsByMemberIdAndTutorialId(memberId, tutorialId)) {
            log.warn("[튜토리얼 완료 실패] 이미 완료된 튜토리얼: tutorialId={}, memberId={}", tutorialId, memberId);
            throw new CustomException(ErrorCode.TUTORIAL_ALREADY_COMPLETED);
        }

        MemberStamp memberStamp = MemberStamp.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .build();

        memberStampRepository.save(memberStamp);

        return CompleteTutorialResponse.of(stamp.getId(), tutorialId);
    }
}
