package com.example.ingle.domain.feedback.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "피드백 응답")
public record FeedbackResponse(

        @Schema(description = "회원 Id", example = "1")
        Long memberId,

        @Schema(description = "내용", example = "So Good Service ~~~")
        String content

) {
    public static FeedbackResponse from(Long memberId, String content) {
        return new FeedbackResponse(memberId, content);
    }
}
