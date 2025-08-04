package com.example.ingle.domain.tutorial.dto.req;

import com.example.ingle.domain.tutorial.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "튜토리얼 수정 요청 DTO")
public class UpdateTutorialRequestDto {

    @Schema(description = "튜토리얼 제목", example = "Transportation Guide")
    private String title;

    @Schema(description = "튜토리얼 카테고리", example = "CAMPUS_LIFE")
    private Category category;

    @Min(value = 1, message = "리워드 위치는 1 이상이어야 합니다.")
    @Schema(description = "리워드 칸 위치\n" +
            "[1][2][3]\n" +
            "[4][5][6]\n" +
            "[7][8][9]\n" +
            "[10][11][12]",
            example = "1")
    private Integer rewardPosition;
}