package com.example.ingle.domain.tutorial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    /**
     * 특정 카테고리의 튜토리얼을 튜토리얼 ID 순으로 조회
     * @param category
     * @return
     */
    @Query("SELECT t FROM Tutorial t WHERE t.category = :category ORDER BY t.id")
    List<Tutorial> findByCategoryOrderById(@Param("category") Category category);

    /**
     * 튜토리얼 ID 기준 정렬
     * @return
     */
    List<Tutorial> findAllByOrderById();
}
