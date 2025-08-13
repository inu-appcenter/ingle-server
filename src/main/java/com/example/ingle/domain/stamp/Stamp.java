package com.example.ingle.domain.stamp;

import com.example.ingle.global.BaseEntity;
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
public class Stamp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId;

    @Column(nullable = false, length = 20)
    private String name; // 스탬프 이름

    @Column(nullable = false, length = 20)
    private String cardtitle; // 카드 제목

    @Column(nullable = false, length = 100)
    private String keword; // 카드 내 키워드

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    private LocalDateTime completedAt;

    @Builder
    public Stamp(Long memberId, Long tutorialId, String name, String cardtitle, String keword, String imageUrl, Boolean isCompleted, LocalDateTime completedAt) {
        this.memberId = memberId;
        this.tutorialId = tutorialId;
        this.name = name;
        this.cardtitle = cardtitle;
        this.keword = keword;
        this.imageUrl = imageUrl;
        this.isCompleted = isCompleted;
        this.completedAt = completedAt;
    }

    public static Stamp complete(Long memberId, Long tutorialId, String name, String cardtitle, String keword, String imageUrl, Boolean isCompleted, LocalDateTime completedAt) {
        return Stamp.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .name(name)
                .cardtitle(cardtitle)
                .keword(keword)
                .imageUrl(imageUrl)
                .isCompleted(isCompleted)
                .completedAt(completedAt)
                .build();
    }
}
