package com.example.ingle.domain.stamp.repository;

import com.example.ingle.domain.stamp.entity.MemberStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberStampRepository extends JpaRepository<MemberStamp, Long> {

    List<MemberStamp> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndTutorialId(Long memberId, Long tutorialId);

    @Modifying
    @Query("DELETE FROM MemberStamp ms WHERE ms.memberId = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);
}