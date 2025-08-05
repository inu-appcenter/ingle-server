package com.example.ingle.domain.memberreward;

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
@Table(name = "member_reward",
uniqueConstraints = @UniqueConstraint(
        name = "uk_member_tutorial", // 제약조건 이름
        columnNames = {"member_id", "tutorial_id"} //사용자, 튜토리얼 조합 유니크
))
public class MemberReward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId; //Member.id

    @Column(name = "tutorial_id", nullable = false)
    private Long tutorialId; //Tutorial.id

    @Column(name = "reward_position", nullable = false)
    private Integer rewardPosition;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Builder
    public MemberReward(Long memberId, Long tutorialId, Integer rewardPosition, Boolean isCompleted,LocalDateTime completedAt) {
        this.memberId = memberId;
        this.tutorialId = tutorialId;
        this.rewardPosition = rewardPosition;
        this.isCompleted = isCompleted;
        this.completedAt = LocalDateTime.now();
    }

    public static MemberReward complete(Long id, Long memberId, Long tutorialId, Integer RewardPosition, Boolean isCompleted,LocalDateTime completedAt) {
        return MemberReward.builder()
                .memberId(memberId)
                .tutorialId(tutorialId)
                .rewardPosition(RewardPosition)
                .isCompleted(false)
                .completedAt(completedAt)
                .build();
    }
}
