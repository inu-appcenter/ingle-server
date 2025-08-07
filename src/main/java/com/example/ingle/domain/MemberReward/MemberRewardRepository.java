package com.example.ingle.domain.memberreward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRewardRepository extends JpaRepository<MemberReward, Long> {


    Optional<MemberReward> findByMemberIdAndRewardPosition(Long memberId, Integer rewardPosition);

    // 특정 튜토리얼 완료 여부만 확인
    boolean existsByMemberIdAndTutorialId(Long memberId, Long tutorialId);

    // 특정 멤버의 (완료된)튜토리얼 ID 목록 조회 (획득 여부 표시용)
    @Query("SELECT ur.tutorialId FROM MemberReward ur WHERE ur.memberId = :memberId")
    List<Long> findCompletedTutorialIdsByMemberId(@Param("memberId") Long memberId);

    // 특정 회원이 완료한 튜토리얼 개수 조회 (진도 계산용)
    @Query("SELECT COUNT(mr) FROM MemberReward mr WHERE mr.memberId = :memberId")
    Long countCompletedTutorialsByMemberId(@Param("memberId") Long memberId);

    // 특정 회원의 완료 기록을 최신순으로 조회
    @Query("SELECT mr FROM MemberReward mr WHERE mr.memberId = :memberId ORDER BY mr.completedAt DESC")
    List<MemberReward> findByMemberIdOrderByCompletedAtDesc(@Param("memberId") Long memberId);

    // 특정 튜토리얼을 완료한 회원의 수 조회 (통계용)
    @Query("SELECT COUNT(mr) FROM MemberReward mr WHERE mr.tutorialId = :tutorialId")
    Long countCompletedMembersByTutorialId(@Param("tutorialId") Long tutorialId);
}

