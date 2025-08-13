package com.example.ingle.domain.progress.res;

import com.example.ingle.domain.progress.Progress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "튜토리얼 완료 처리 응답 DTO")
public class CompleteTutorialResponse {

    @Schema(description = "튜토리얼 ID", example = "1")
    private final Long tutorialId;

    @Schema(description = "스탬프 ID", example = "1")
    private final Long stampId;

    @Schema(description = "완료 시간", example = "2025-01-15T14:30:00")
    private final LocalDateTime completedAt;

    @Builder
    private CompleteTutorialResponse(Progress progress) {
        this.tutorialId = progress.getTutorialId();
        this.stampId = progress.getStampId();
        this.completedAt = progress.getCompletedAt();
    }
}
