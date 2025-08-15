package com.example.ingle.domain.stamp.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "튜토리얼 진행률 응답 DTO")
public class ProgressResponse {

    @Schema(description = "완료된 튜토리얼 수", example = "3")
    private final Integer completedCount;

    @Schema(description = "전체 튜토리얼 수", example = "13")
    private final Integer totalCount;

    @Builder
    private ProgressResponse(Integer completedCount, Integer totalCount) {
        this.completedCount = completedCount;
        this.totalCount = totalCount;
    }
}
