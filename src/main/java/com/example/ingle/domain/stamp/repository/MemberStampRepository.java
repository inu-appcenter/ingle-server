package com.example.ingle.domain.stamp.repository;

import com.example.ingle.admin.statistics.dto.AdminStampProgress;
import com.example.ingle.domain.stamp.entity.MemberStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberStampRepository extends JpaRepository<MemberStamp, Long> {

    List<MemberStamp> findAllByMemberId(Long memberId);
    boolean existsByMemberIdAndTutorialId(Long memberId, Long tutorialId);
    long countByTutorialId(Long tutorialId); // 특정 튜토리얼 완료한 멤버 수

    @Modifying
    @Query("DELETE FROM MemberStamp ms WHERE ms.memberId = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);

    /*
     * 스탬프 획득 현황 조회용(N+1 방지)
     */
    @Query("""
        SELECT new com.example.ingle.admin.statistics.dto.AdminStampProgress(
            s.name,
            COUNT(ms.id)
        )
        FROM Stamp s
        LEFT JOIN MemberStamp ms ON s.tutorialId = ms.tutorialId
        GROUP BY s.id, s.name
        ORDER BY s.id
        """)
    List<AdminStampProgress> findStampProgress();
}