package com.example.ingle.domain.member.dto.req;

import com.example.ingle.domain.member.enums.Department;
import com.example.ingle.domain.member.enums.StudentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원 정보 입력 요청 DTO")
public class MemberInfoRequest {

    @Pattern(regexp = "\\d{9}", message = "학번은 9자리 숫자여야 합니다.")
    @NotBlank(message = "학번이 비어있습니다.")
    @Schema(description = "학번", example = "202301452")
    private String studentId;

    @NotNull(message = "학과가 비어있습니다.")
    @Schema(description = "학과", example = "COMPUTER_ENGINEERING")
    private Department department;

    @NotNull(message = "학생 유형이 비어있습니다.")
    @Schema(description = "학생 유형", example = "EXCHANGE")
    private StudentType studentType;

    @Size(min = 3, max = 20, message = "닉네임은 3~20자여야 합니다.")
    @NotBlank(message = "닉네임이 비어있습니다.")
    @Schema(description = "닉네임", example = "IngleFan")
    private String nickname;
}
