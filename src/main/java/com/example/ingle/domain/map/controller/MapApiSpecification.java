package com.example.ingle.domain.map.controller;

import com.example.ingle.domain.map.MapCategory;
import com.example.ingle.domain.map.dto.res.MapDetailResponseDto;
import com.example.ingle.domain.map.dto.res.MapResponseDto;
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

import java.util.List;

@Tag(name = "Map", description = "지도 관련 API")
public interface MapApiSpecification {

    @Operation(
            summary = "지도 범위 조회",
            description = "화면 범위 내의 존재하는 지도 값들을 반환합니다." +
                    "<br><br>카테고리를 선택하지 않으면 모든 카테고리의 지도를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "지도 범위 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MapResponseDto.class))
                            )
                    ),
            }
    )
    ResponseEntity<List<MapResponseDto>> getMapsInBounds(
            @RequestParam double minLat, @RequestParam double minLng,
            @RequestParam double maxLat, @RequestParam double maxLng,
            @RequestParam(required = false) MapCategory category);

    @Operation(
            summary = "건물 상세 조회",
            description = "지도에서 건물을 클릭해 건물 정보를 상세 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "건물 상세 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MapDetailResponseDto.class)
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
    ResponseEntity<MapDetailResponseDto> getMapDetail(@PathVariable Long mapId);

    @Operation(
            summary = "지도 검색",
            description = "지도에서 키워드를 기반으로 건물을 검색합니다." +
                    "<br><br>해당 키워드가 포함된 건물들을 모두 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "지도 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MapResponseDto.class))
                            )
                    )
            }
    )
    ResponseEntity<List<MapResponseDto>> searchMaps(@RequestParam String keyword);
}
