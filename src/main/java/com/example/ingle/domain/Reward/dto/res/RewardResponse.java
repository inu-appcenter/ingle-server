package com.example.ingle.domain.reward.dto.res;

import com.example.ingle.domain.reward.Reward;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "리워드 응답 DTO")
public class RewardResponse {

    @Schema(description = "리워드 ID", example = "1")
    private final Long id;

    @Schema(description = "리워드 위치\n" +
            "[1][2][3]\n" +
            "[4][5][6]\n" +
            "[7][8][9]...",
            example = "1")
    private final Integer position;

    @Schema(description = "리워드 이름", example = "Transit")
    private final String name;

    @Schema(description = "리워드 이미지", example = "https://ingle-server.inuappcenter.kr/images/reward_1.png")
    private final String rewardImageUrl;

    @Builder
    public RewardResponse(Reward reward) {
        this.id = reward.getId();
        this.position = reward.getPosition();
        this.name = reward.getName();
        this.rewardImageUrl = reward.getRewardImageUrl();
    }
}
