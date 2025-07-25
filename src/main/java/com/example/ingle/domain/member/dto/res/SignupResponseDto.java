package com.example.ingle.domain.member.dto.res;

import com.example.ingle.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 응답 DTO")
public class SignupResponseDto {

    @Schema(description = "학과", example = "COMPUTER_ENG")
    private final String department;

    @Schema(description = "파견 유형", example = "EXCHANGE")
    private final String program;

    @Schema(description = "회원가입된 닉네임", example = "IngleFan")
    private final String nickname;

    @Schema(description = "이용 약관 동의 여부", example = "true")
    private final boolean termsAgreed;

    @Builder
    private SignupResponseDto(Member member) {
        this.department = member.getDepartment().name();
        this.program = member.getProgram().name();
        this.nickname = member.getNickname();
        this.termsAgreed = member.isTermsAgreed();
    }
}