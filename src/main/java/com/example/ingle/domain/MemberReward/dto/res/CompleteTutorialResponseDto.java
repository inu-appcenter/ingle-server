package com.example.ingle.domain.memberreward.dto.res;

import com.example.ingle.domain.memberreward.MemberReward;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "튜토리얼 완료 처리 응답 DTO")
public class CompleteTutorialResponseDto {

    @Schema(description = "튜토리얼 ID", example = "1")
    private final Long tutorialId;

    @Schema(description = "리워드 위치", example = "1")
    private final Integer rewardPosition;

    @Schema(description = "완료 시간", example = "2025-01-15T14:30:00")
    private final LocalDateTime completedAt;

    @Builder
    private CompleteTutorialResponseDto(MemberReward memberReward) {
        this.tutorialId = memberReward.getTutorialId();
        this.rewardPosition = memberReward.getRewardPosition();
        this.completedAt = memberReward.getCompletedAt();
    }
}
