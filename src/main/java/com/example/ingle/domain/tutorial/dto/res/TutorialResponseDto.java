package com.example.ingle.domain.tutorial.dto.res;

import com.example.ingle.domain.tutorial.Category;
import com.example.ingle.domain.tutorial.Tutorial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "튜토리얼 조회 응답 DTO")
public class TutorialResponseDto {

    @Schema(description = "튜토리얼 Id", example = "1")
    private final Long tutorialId;

    @Schema(description = "튜토리얼 제목", example = "Transportation")
    private final String title;

    @Schema(description = "튜토리얼 카테고리", example = "CAMPUS_LIFE")
    private final Category category;

    @Schema(description = "리워드 칸 위치 (1~13)\n" +
            "[1][2][3]\n" +
            "[4][5][6]\n" +
            "[7][8][9]\n",
            example = "1")
    private final Integer rewardPosition;

    @Builder
    public TutorialResponseDto(Tutorial tutorial) {
        this.tutorialId = tutorial.getId();
        this.title = tutorial.getTitle();
        this.category = tutorial.getCategory();
        this.rewardPosition = tutorial.getRewardPosition();
    }
}
