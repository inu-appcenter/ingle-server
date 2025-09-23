package com.example.ingle.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원 탈퇴 사유 요청")
public record WithdrawalReasonRequest(

        @Schema(description = "내용", example = "No longer using the service")
        @NotBlank(message = "내용이 비어있습니다.")
        String content

) {
}
