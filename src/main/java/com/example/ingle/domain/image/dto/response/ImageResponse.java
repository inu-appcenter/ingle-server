package com.example.ingle.domain.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미지 처리 응답")
public record ImageResponse(

        @Schema(description = "파일 명")
        String fileName,

        @Schema(description = "파일 url")
        String url
) {
    public static ImageResponse from(String fileName, String url) {
        return new ImageResponse(fileName, url);
    }
}
