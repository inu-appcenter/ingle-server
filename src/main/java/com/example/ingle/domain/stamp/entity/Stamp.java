package com.example.ingle.domain.stamp.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stamp")
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId;

    @Column(nullable = false, length = 20)
    private String name; // 스탬프 이름

    @Column(nullable = false, length = 20)
    private String cardTitle; // 카드 제목

    @Column(nullable = false, length = 100)
    private String keyword; // 카드 내 키워드

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public Stamp(Long tutorialId, String name, String cardTitle, String keyword, String imageUrl) {
        this.tutorialId = tutorialId;
        this.name = name;
        this.cardTitle = cardTitle;
        this.keyword = keyword;
        this.imageUrl = imageUrl;
    }
}
