package com.example.ingle.domain.image.controller;

import com.example.ingle.domain.building.dto.req.ImageUploadRequest;
import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Image", description = "이미지 관련 API")
public interface ImageApiSpecification {

    @Operation(summary = "이미지 조회",
            description = "이미지 파일 명을 기반으로 이미지를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "이미지 조회 성공",
                            content = @Content(
                                    mediaType = "image/png"
                            )
                    )
            }
    )
    ResponseEntity<byte[]> getImage(@PathVariable String filename);


    @Operation(summary = "이미지 업로드",
            description = "이미지를 업로드합니다. <br><br>" + "파일명과 카테고리를 보내주세요.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "이미지 업로드 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<ImageResponse> uploadImage(@Valid @RequestPart ImageUploadRequest request,
                                              @RequestPart("image") MultipartFile image);
}
