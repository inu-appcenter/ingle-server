package com.example.ingle.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupRequiredResponse(
    @Schema(description = "학번", example = "202301452") String studentId,
    @Schema(description = "회원가입 필요 여부", example = "true") boolean signupRequired,
    @Schema(description = "메시지", example = "회원가입이 필요합니다.") String message
) implements LoginResponse {
}
