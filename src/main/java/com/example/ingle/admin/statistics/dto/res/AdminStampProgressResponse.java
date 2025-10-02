package com.example.ingle.admin.statistics.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관리자 스탬프 획득률 조회 DTO")
public record AdminStampProgressResponse(

        @Schema(description = "스탬프(튜토리얼) 이름", example = "Transit")
        String stampName,

        @Schema(description = "해당 스탬프를 획득한 회원 수", example = "145")
        Long acquiredCount,

        @Schema(description = "전체 회원 수", example = "150")
        Long totalCount

) {
    public static AdminStampProgressResponse of(String stampName, long acquiredCount, long totalCount) {
        return new AdminStampProgressResponse(stampName, acquiredCount, totalCount);
    }
}