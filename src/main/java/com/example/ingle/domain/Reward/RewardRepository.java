package com.example.ingle.domain.reward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    // 모든 리워드를 위치 순으로 조회
    @Query("SELECT r FROM Reward r ORDER BY r.position")
    List<Reward> findAllOrderByPosition();

    // 특정 위치의 리워드 조회
    Optional<Reward> findByPosition(Integer position);
}
