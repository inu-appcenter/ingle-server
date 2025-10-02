package com.example.ingle.domain.stamp.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "튜토리얼 완료 처리 응답 DTO")
public record CompleteTutorialResponse(

        @Schema(description = "스탬프 ID", example = "1")
        Long id,

        @Schema(description = "튜토리얼 ID", example = "1")
        Long tutorialId

) {
    public static CompleteTutorialResponse of(Long id, Long tutorialId) {
        return new CompleteTutorialResponse(id, tutorialId);
    }
}