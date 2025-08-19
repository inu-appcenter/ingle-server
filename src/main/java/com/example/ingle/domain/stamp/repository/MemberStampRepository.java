package com.example.ingle.domain.stamp.repository;

import com.example.ingle.domain.stamp.entity.MemberStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberStampRepository extends JpaRepository<MemberStamp, Long> {

    Optional<MemberStamp> findByMemberIdAndTutorialId(Long memberId, Long tutorialId);

    List<MemberStamp> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndTutorialId(Long memberId, Long tutorialId);

    // 특정 멤버의 스탬프 획득 개수 조회
    @Query("SELECT COUNT(ms) FROM MemberStamp ms WHERE ms.memberId = :memberId")
    Long countCompletedByMemberId(@Param("memberId") Long memberId);
}