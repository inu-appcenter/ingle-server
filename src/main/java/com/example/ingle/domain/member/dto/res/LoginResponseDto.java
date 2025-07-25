package com.example.ingle.domain.member.dto.res;

import com.example.ingle.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {

    @Schema(description = "회원 Id", example = "1")
    private final Long memberId;

    @Schema(description = "학번", example = "202301452")
    private final String studentId;

    @Schema(description = "학과", example = "COMPUTER_ENG")
    private final String department;

    @Schema(description = "파견 유형", example = "EXCHANGE")
    private final String program;

    @Schema(description = "닉네임", example = "IngleFan")
    private final String nickname;

    @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String accessToken;

    @Schema(description = "JWT Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String refreshToken;

    @Schema(description = "Access Token 만료 시간 (Epoch milliseconds)", example = "7195000")
    private final Long accessTokenExpires;

    @Schema(description = "Access Token 만료 일시 (Date)", example = "2025-05-14T15:30:00.000+09:00")
    private final Date accessTokenExpiresDate;

    @Builder
    private LoginResponseDto(Member member, String accessToken, String refreshToken,
                             Long accessTokenExpires, Date accessTokenExpiresDate) {
        this.memberId = member.getId();
        this.studentId = member.getStudentId();
        this.department = member.getDepartment().name();
        this.program = member.getProgram().name();
        this.nickname = member.getNickname();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpires = accessTokenExpires;
        this.accessTokenExpiresDate = accessTokenExpiresDate;
    }
}
