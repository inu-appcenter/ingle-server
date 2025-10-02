package com.example.ingle.domain.tutorial.dto.res;

import com.example.ingle.domain.tutorial.Category;
import com.example.ingle.domain.tutorial.Tutorial;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "튜토리얼 조회 응답 DTO")
public record TutorialResponse(

        @Schema(description = "튜토리얼 Id", example = "1")
        Long tutorialId,

        @Schema(description = "튜토리얼 제목", example = "Transportation")
        String title,

        @Schema(description = "튜토리얼 카테고리", example = "CAMPUS_LIFE")
        Category category

) {
    public static TutorialResponse from(Tutorial tutorial) {
        return new TutorialResponse(
                tutorial.getId(),
                tutorial.getTitle(),
                tutorial.getCategory()
        );
    }
}