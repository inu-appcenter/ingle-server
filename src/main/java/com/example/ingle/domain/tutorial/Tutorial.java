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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 100)
    private String image;

    public Tutorial(String title, String content, Category category, String image) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.image = image;
    }

    // 제목 업데이트
    public void updateTitle(String title) {
        this.title = title;
    }

    // 내용 업데이트
    public void updateContent(String content) {
        this.content = content;
    }

    // 카테고리 업데이트
    public void updateCategory(Category category) {
        this.category = category;
    }

    // 이미지 업데이트
    public void updateImage(String image) {
        this.image = image;
    }
}
