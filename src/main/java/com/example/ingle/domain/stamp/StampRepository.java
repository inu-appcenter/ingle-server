package com.example.ingle.domain.stamp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    // 모든 스탬프를 ID순으로 조회
    List<Stamp> findAllByOrderById();
}
