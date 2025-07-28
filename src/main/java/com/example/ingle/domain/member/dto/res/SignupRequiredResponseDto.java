package com.example.ingle.domain.member.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 필요 응답 DTO")
public class SignupRequiredResponseDto {

    @Schema(description = "학번", example = "202301452")
    private final String studentId;

    private final boolean loginRequired = true;

    public SignupRequiredResponseDto(String studentId) {
        this.studentId = studentId;
    }
}
