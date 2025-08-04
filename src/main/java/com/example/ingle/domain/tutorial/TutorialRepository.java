package com.example.ingle.domain.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    // 특정 카테고리의 튜토리얼을 리워드 위치 순으로 조회 (1→2→3→...)
    @Query("SELECT t FROM Tutorial t WHERE t.category = :category ORDER BY t.rewardPosition")
    List<Tutorial> findByCategoryOrderByRewardPosition(@Param("category") Category category);

    // 특정 리워드 위치에 이미 연결된 튜토리얼이 있는지 확인 (중복 방지)
    boolean existsByRewardPosition(Integer rewardPosition);

    // 리워드 위치 기준 정렬
    List<Tutorial> findAllByOrderByRewardPosition();
}
