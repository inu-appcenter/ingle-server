package com.example.ingle.domain.stamp.entity;

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
@Table(name = "member_stamp",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "tutorial_id"})
        }
) // 스탬프 기본 정보와 튜토리얼 완료 시간 분리
public class MemberStamp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Builder
    public MemberStamp(Long memberId, Long tutorialId) {
        this.memberId = memberId;
        this.tutorialId = tutorialId;
        this.completedAt = LocalDateTime.now();
    }
}