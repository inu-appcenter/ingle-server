package com.example.ingle.domain.reward.dto.res;

import com.example.ingle.domain.reward.Reward;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "리워드 상세 정보 응답 DTO")
public class RewardCardResponseDto {

    @Schema(description = "리워드 ID", example = "1")
    private final Long id;

    @Schema(description = "리워드 제목", example = "Transportation")
    private final String title;

    @Schema(description = "리워드 키워드", example = "Airport, Subway, Buses, Bike")
    private final String keword;

    @Schema(description = "리워드 이미지", example = "https://ingle-server.inuappcenter.kr/images/reward_1.png")
    private final String rewardImageUrl;

    @Builder
    public RewardCardResponseDto(Reward reward) {
        this.id = reward.getId();
        this.title = reward.getTitle();
        this.keword = reward.getKeword();
        this.rewardImageUrl = reward.getRewardImageUrl();
    }

}
