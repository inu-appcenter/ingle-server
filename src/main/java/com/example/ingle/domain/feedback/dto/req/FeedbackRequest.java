package com.example.ingle.domain.feedback.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "피드백 전송 요청")
public record FeedbackRequest(

        @Schema(description = "내용", example = "So Good Service ~~~")
        @NotBlank(message = "내용이 비어있습니다.")
        String content

) {
}
