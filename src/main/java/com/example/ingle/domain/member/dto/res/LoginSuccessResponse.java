package com.example.ingle.domain.member.dto.res;

import com.example.ingle.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Date;

@Schema(description = "로그인 응답 DTO")
public record LoginSuccessResponse(

        @Schema(description = "회원 Id", example = "1")
        Long memberId,

        @Schema(description = "학번", example = "202301452")
        String studentId,

        @Schema(description = "학과", example = "Dept. of Computer Science & Engineering")
        String department,

        @Schema(description = "학생 유형", example = "Exchange Student")
        String studentType,

        @Schema(description = "닉네임", example = "IngleFan")
        String nickname,

        @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "JWT Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String refreshToken,

        @Schema(description = "Access Token 만료 일시 (Date)", example = "2025-05-14T15:30:00.000+09:00")
        Date accessTokenExpiresDate,

        @Schema(description = "Refresh Token 만료 일시 (Date)", example = "2025-05-14T15:30:00.000+09:00")
        Date refreshTokenExpiresDate

) implements LoginResponse {
    @Builder
    public static LoginSuccessResponse from(Member member, String accessToken, String refreshToken,
                                            Date accessTokenExpiresDate, Date refreshTokenExpiresDate) {
        return new LoginSuccessResponse(
                member.getId(),
                member.getStudentId(),
                member.getDepartment().getFullName(),
                member.getStudentType().getDescription(),
                member.getNickname(),
                accessToken,
                refreshToken,
                accessTokenExpiresDate,
                refreshTokenExpiresDate
        );
    }
}