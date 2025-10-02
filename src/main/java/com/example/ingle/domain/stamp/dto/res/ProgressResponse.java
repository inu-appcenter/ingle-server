package com.example.ingle.domain.stamp.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "튜토리얼 진행률 응답 DTO")
public record ProgressResponse(

        @Schema(description = "완료된 튜토리얼 수", example = "3")
        Integer completedCount,

        @Schema(description = "전체 튜토리얼 수", example = "13")
        Integer totalCount

) {
    public static ProgressResponse of(Integer completedCount, Integer totalCount) {
        return new ProgressResponse(completedCount, totalCount);
    }
}