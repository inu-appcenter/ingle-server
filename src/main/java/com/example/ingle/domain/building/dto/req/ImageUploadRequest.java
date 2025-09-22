package com.example.ingle.domain.building.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "이미지 업로드 요청")
public class ImageUploadRequest {

        @Schema(description = "파일명", example = "Crab")
        @NotBlank(message = "파일명이 비어있습니다.")
        private String name;

        @Schema(description = "카테고리", example = "profile-image")
        @NotBlank(message = "카테고리가 비어있습니다.")
        private String category;
}
