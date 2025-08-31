package com.example.ingle.admin.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "관리자 회원 수 조회 응답 DTO")
public record AdminMemberCountResponse(

        @Schema(description = "전체 회원 수", example = "150")
        Long totalCount,

        @Schema(description = "활성 회원 수", example = "145")
        Long activeCount,

        @Schema(description = "밴된 회원 수", example = "5")
        Long bannedCount
) {
    @Builder
    public AdminMemberCountResponse {
    }
}