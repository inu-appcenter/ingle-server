package com.example.ingle.domain.stamp.repository;

import com.example.ingle.domain.stamp.entity.MemberStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStampRepository extends JpaRepository<MemberStamp, Long> {

    List<MemberStamp> findAllByMemberId(Long memberId);

    boolean existsByMemberIdAndTutorialId(Long memberId, Long tutorialId);
}