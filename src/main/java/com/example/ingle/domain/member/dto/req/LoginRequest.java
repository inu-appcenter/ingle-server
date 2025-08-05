package com.example.ingle.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
public record LoginRequest(

        @Schema(description = "사용자의 학번", example = "202301452")
        @NotBlank(message = "학번이 비어있습니다.")
        String studentId,

        @Schema(description = "사용자의 비밀번호", example = "password123")
        @NotBlank(message = "비밀번호가 비어있습니다.")
        String password

) {
}