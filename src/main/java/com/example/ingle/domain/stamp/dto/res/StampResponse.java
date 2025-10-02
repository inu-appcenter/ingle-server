package com.example.ingle.domain.stamp.dto.res;

import com.example.ingle.domain.stamp.entity.Stamp;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스탬프 상세 정보 응답 DTO")
public record StampResponse(

        @Schema(description = "스탬프 ID", example = "1")
        Long id,

        @Schema(description = "스탬프 이름", example = "Transit")
        String name,

        @Schema(description = "스탬프 키워드", example = "Airport, Subway, Buses, Bike")
        String keyword,

        @Schema(description = "리워드 이미지", example = "https://ingle-server.inuappcenter.kr/images/reward_1.png")
        String imageUrl,

        @Schema(description = "완료 여부", example = "true")
        boolean isCompleted

) {
    public static StampResponse from(Stamp stamp, boolean isCompleted) {
        return new StampResponse(
                stamp.getId(),
                stamp.getName(),
                stamp.getKeyword(),
                stamp.getImageUrl(),
                isCompleted
        );
    }
}