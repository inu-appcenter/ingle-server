package com.example.ingle.domain.tutorial;

import com.example.ingle.domain.memberreward.MemberReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    Optional<Tutorial> findByRewardPosition(Integer rewardPosition);

    // 특정 카테고리의 튜토리얼을 리워드 위치 순으로 조회 (1→2→3→...)
    @Query("SELECT t FROM Tutorial t WHERE t.category = :category ORDER BY t.rewardPosition")
    List<Tutorial> findByCategoryOrderByRewardPosition(@Param("category") Category category);

    // 리워드 위치 기준 정렬
    List<Tutorial> findAllByOrderByRewardPosition();
}
