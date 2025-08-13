package com.example.ingle.domain.progress;

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
@Table(name = "progress",
uniqueConstraints = @UniqueConstraint(
        name = "uk_member_tutorial", // 제약조건 이름
        columnNames = {"member_id", "tutorial_id"} //사용자, 튜토리얼 조합 유니크
))
public class Progress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId; //Member.id

    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId; //Tutorial.id

    @Column(name = "stamp_id", nullable = false)
    private Long stampId;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Builder
    public Progress(Long memberId, Long tutorialId, Long stampId, Boolean isCompleted, LocalDateTime completedAt) {
        this.memberId = memberId;
        this.tutorialId = tutorialId;
        this.stampId = stampId;
        this.isCompleted = isCompleted;
        this.completedAt = LocalDateTime.now();
    }

    public static Progress complete(Long id, Long memberId, Long tutorialId, Long stampId, Boolean isCompleted, LocalDateTime completedAt) {
        return Progress.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .stampId(stampId)
                .isCompleted(false)
                .completedAt(completedAt)
                .build();
    }
}
