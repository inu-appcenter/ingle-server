package com.example.ingle.domain.member.dto.req;

import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.Program;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignupRequestDto {

    @NotBlank(message = "학번이 비어있습니다.")
    @Schema(description = "사용자의 학번", example = "202301452")
    private String studentId;

    @NotNull(message = "학과가 비어있습니다.")
    @Schema(description = "사용자의 학과", example = "COMPUTER_ENG")
    private Department department;

    @NotNull(message = "파견 유형이 비어있습니다.")
    @Schema(description = "사용자의 파견 유형", example = "EXCHANGE")
    private Program program;

    @Size(min = 2, max = 20)
    @NotBlank(message = "닉네임이 비어있습니다.")
    @Schema(description = "사용자의 닉네임", example = "IngleFan")
    private String nickname;

    @NotNull(message = "이용 약관 동의 여부가 비어있습니다.")
    @Schema(description = "이용 약관 동의 여부", example = "true")
    private boolean termsAgreed;
}
