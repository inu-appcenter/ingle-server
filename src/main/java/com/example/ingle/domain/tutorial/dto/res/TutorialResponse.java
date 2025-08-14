package com.example.ingle.domain.tutorial.dto.res;

import com.example.ingle.domain.tutorial.Category;
import com.example.ingle.domain.tutorial.Tutorial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "튜토리얼 조회 응답 DTO")
public class TutorialResponse {

    @Schema(description = "튜토리얼 Id", example = "1")
    private final Long tutorialId;

    @Schema(description = "튜토리얼 제목", example = "Transportation")
    private final String title;

    @Schema(description = "튜토리얼 카테고리", example = "CAMPUS_LIFE")
    private final Category category;

    @Schema(description = "스탬프 식별자", example = "1")
    private final Long stampId;

    @Builder
    public TutorialResponse(Tutorial tutorial) {
        this.tutorialId = tutorial.getId();
        this.title = tutorial.getTitle();
        this.category = tutorial.getCategory();
        this.stampId = tutorial.getStampId();
    }
}
