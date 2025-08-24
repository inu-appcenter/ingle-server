package com.example.ingle.domain.stamp.res;

import com.example.ingle.domain.stamp.entity.Stamp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "튜토리얼 완료 처리 응답 DTO")
public class CompleteTutorialResponse {

    @Schema(description = "스탬프 ID", example = "1")
    private final Long id;

    @Schema(description = "튜토리얼 ID", example = "1")
    private final Long tutorialId;

    @Builder
    private CompleteTutorialResponse(Long id, Long tutorialId) {
        this.id = id;
        this.tutorialId = tutorialId;
    }
}
