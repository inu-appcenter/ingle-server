package com.example.ingle.domain.tutorial;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository {
    // 카테고리별 튜토리얼 조회
    List<Tutorial> findByCategory(Category category);
}
