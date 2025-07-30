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

    @Schema(description = "학과", example = "Dept. of Computer Science & Engineering")
    private final String department;

    @Schema(description = "파견 유형", example = "Exchange Student")
    private final String program;

    @Schema(description = "닉네임", example = "IngleFan")
    private final String nickname;

    @Schema(description = "이용 약관 동의 여부", example = "true")
    private final boolean termsAgreed;

    @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String accessToken;

    @Schema(description = "JWT Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String refreshToken;

    @Schema(description = "Access Token 만료 일시 (Date)", example = "2025-05-14T15:30:00.000+09:00")
    private final Date accessTokenExpiresDate;

    @Schema(description = "Refresh Token 만료 일시 (Date)", example = "2025-05-14T15:30:00.000+09:00")
    private final Date refreshTokenExpiresDate;

    @Builder
    private LoginResponseDto(Member member, String accessToken, String refreshToken,
                             Date accessTokenExpiresDate, Date refreshTokenExpiresDate) {
        this.memberId = member.getId();
        this.studentId = member.getStudentId();
        this.department = member.getDepartment().name();
        this.program = member.getProgram().name();
        this.nickname = member.getNickname();
        this.termsAgreed = member.isTermsAgreed();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresDate = accessTokenExpiresDate;
        this.refreshTokenExpiresDate = refreshTokenExpiresDate;
    }
}
