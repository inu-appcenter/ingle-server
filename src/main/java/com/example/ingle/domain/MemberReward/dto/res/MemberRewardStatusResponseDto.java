package com.example.ingle.domain.memberreward.dto.res;

import com.example.ingle.domain.memberreward.MemberReward;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "리워드 획득 여부 응답 DTO")
public class MemberRewardStatusResponseDto {

    @Schema(description = "리워드 위치", example = "1")
    private final Integer rewardPosition;

    @Schema(description = "해당 튜토리얼 ID", example = "1")
    private final Long tutorialId;

    @Schema(description = "완료 여부", example = "true")
    private final Boolean isCompleted;

    @Schema(description = "완료 시간", example = "2025-01-15T14:30:00")
    private final LocalDateTime completedAt;

    @Builder
    private MemberRewardStatusResponseDto(Integer rewardPosition, Long tutorialId, Boolean isCompleted, LocalDateTime completedAt) {
        this.rewardPosition = rewardPosition;
        this.tutorialId = tutorialId;
        this.isCompleted = isCompleted;
        this.completedAt = completedAt;
    }

    // 완료
    public static MemberRewardStatusResponseDto fromMemberReward(MemberReward memberReward) {
        return MemberRewardStatusResponseDto.builder()
                .tutorialId(memberReward.getTutorialId())
                .rewardPosition(memberReward.getRewardPosition())
                .isCompleted(memberReward.getIsCompleted())
                .completedAt(memberReward.getCompletedAt())
                .build();
    }

    // 미완료
    public static MemberRewardStatusResponseDto notCompleted(Long tutorialId, Integer rewardPosition) {
        return MemberRewardStatusResponseDto.builder()
                .tutorialId(tutorialId)
                .rewardPosition(rewardPosition)
                .isCompleted(false)
                .completedAt(null)
                .build();
    }
}

