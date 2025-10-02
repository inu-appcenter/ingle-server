package com.example.ingle.domain.building.controller;

import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.dto.res.BuildingDetailResponse;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
import com.example.ingle.domain.image.dto.res.ImageResponse;
import com.example.ingle.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Building", description = "건물 관련 API")
public interface BuildingApiSpecification {

    @Operation(
            summary = "지도 범위 내의 건물 조회",
            description = "화면 범위 내의 존재하는 건물 값들을 반환합니다." +
                    "<br><br>카테고리를 선택하지 않으면 모든 카테고리의 건물을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "지도 범위 내의 건물 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BuildingResponse.class))
                            )
                    ),
            }
    )
    ResponseEntity<List<BuildingResponse>> findMapsInBounds(
            @RequestParam double minLat, @RequestParam double minLng,
            @RequestParam double maxLat, @RequestParam double maxLng,
            @RequestParam(required = false) BuildingCategory category);

    @Operation(
            summary = "건물 상세 조회",
            description = "지도에서 건물을 클릭해 건물 정보를 상세 조회합니다. " +
                    "<br><br>이미지는 반환된 buildingImages 값을 통해 이미지 조회 API를 호출하여 가져올 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "건물 상세 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BuildingDetailResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "건물을 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "MAP_NOT_FOUND",
                                                   "message": "건물을 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<BuildingDetailResponse> getMapDetail(@PathVariable Long mapId);

    @Operation(
            summary = "건물 검색",
            description = "지도에서 키워드를 기반으로 건물을 검색합니다." +
                    "<br><br>해당 키워드가 포함된 건물들을 모두 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "건물 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BuildingResponse.class))
                            )
                    )
            }
    )
    ResponseEntity<List<BuildingResponse>> searchMaps(@RequestParam String keyword);

    @Operation(
            summary = "건물 이미지 등록",
            description = "건물 Id를 통해 건물의 이미지를 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "건물 이미지 등록 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ImageResponse.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "건물을 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "MAP_NOT_FOUND",
                                                   "message": "건물을 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "이미지 변환에 실패했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 500,
                                                   "name": "IMAGE_CONVERSION_FAILED",
                                                   "message": "이미지 변환에 실패했습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<ImageResponse>> saveBuildingImages(@PathVariable Long buildingId,
                                                           @RequestPart("images") List<MultipartFile> images);
}
