package com.example.ingle.domain.stamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    List<Stamp> findAllByMemberIdOrderById(Long memberId);

    Optional<Stamp> findByIdAndMemberId(Long id, Long memberId);

    Optional<Stamp> findByMemberIdAndTutorialId(Long memberId, Long tutorialId);

    // 특정 튜토리얼 완료 여부 확인 (completedAt 기준)
    boolean existsByMemberIdAndTutorialIdAndCompletedAtIsNotNull(Long memberId, Long tutorialId);

    // 특정 회원이 완료한 튜토리얼 개수 조회
    @Query("SELECT COUNT(s) FROM Stamp s WHERE s.memberId = :memberId AND s.completedAt IS NOT NULL")
    Long countCompletedTutorialsByMemberId(@Param("memberId") Long memberId);
}
