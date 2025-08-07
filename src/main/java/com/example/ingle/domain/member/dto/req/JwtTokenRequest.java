package com.example.ingle.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "리프레쉬 요청 DTO")
public record JwtTokenRequest(

        @Schema(description = "Refresh Token", example = "qwerqwesdhfaifo...")
        @NotBlank(message = "Refresh Token이 비어있습니다.")
        String refreshToken

) {
}