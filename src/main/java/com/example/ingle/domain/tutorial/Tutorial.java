package com.example.ingle.domain.tutorial;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tutorial")
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Long stampId; // 스탬프 식별자

    public Tutorial(String title, Category category, Long stampId) {
        this.title = title;
        this.category = category;
        this.stampId = stampId;
    }

    // 제목 업데이트
    public void updateTitle(String title) {
        this.title = title;
    }

    // 카테고리 업데이트
    public void updateCategory(Category category) {
        this.category = category;
    }

    // 연결된 스탬프 업데이트
    public void updateRewardPosition(Integer rewardPosition) {
        this.stampId = stampId;
    }
}
