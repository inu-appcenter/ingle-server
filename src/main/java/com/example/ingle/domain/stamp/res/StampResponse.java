package com.example.ingle.domain.stamp.res;

import com.example.ingle.domain.stamp.entity.Stamp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스탬프 상세 정보 응답 DTO")
public class StampResponse {

    @Schema(description = "스탬프 ID", example = "1")
    private final Long id;

    @Schema(description = "스탬프 이름", example = "Transit")
    private final String name;

    @Schema(description = "스탬프 키워드", example = "Airport, Subway, Buses, Bike")
    private final String keyword;

    @Schema(description = "리워드 이미지", example = "https://ingle-server.inuappcenter.kr/images/reward_1.png")
    private final String imageUrl;

    @Schema(description = "완료 여부", example = "true")
    private final boolean isCompleted;

    @Builder
    public StampResponse(Stamp stamp, boolean isCompleted) {
        this.id = stamp.getId();
        this.name = stamp.getName();
        this.keyword = stamp.getKeyword();
        this.imageUrl = stamp.getImageUrl();
        this.isCompleted = isCompleted;
    }

}
