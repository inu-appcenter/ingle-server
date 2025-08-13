package com.example.ingle.domain.stamp.controller;

import com.example.ingle.domain.stamp.res.StampResponse;
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

import java.util.List;

@Tag(name = "Stamp", description = "스탬프 관련 API")
public interface StampApiSpecification {

    @Operation(
            summary = "스탬프 조회",
            description = "ID로 스탬프의 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스탬프 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StampResponse.class),
                                    examples = @ExampleObject(
                                            name = "스탬프 조회 응답 예시",
                                            value = """
                                              {
                                                "id": 1,
                                                "memberId": 123,
                                                "tutorialId": 1,
                                                "name": "Transit",
                                                "cardtitle": "Transportation",
                                                "keword": "Airport, Subway, Buses, Bike",
                                                "rewardImageUrl": null,
                                                "isCompleted": true,
                                                "completedAt": "2025-01-15T14:30:00"
                                              }
                                             """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "스탬프를 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "STAMP_NOT_FOUND",
                                                   "message": "스탬프를 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<StampResponse> getStamp(@PathVariable Long id);

    @Operation(
            summary = "전체 스탬프 목록 조회",
            description = "모든 스탬프를 ID 순으로 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 스탬프 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = StampResponse.class)),
                                    examples = @ExampleObject(
                                            name = "전체 스탬프 목록 응답 예시",
                                            value = """
                                                        [
                                                          {
                                                            "id": 1,
                                                            "memberId": 123,
                                                            "tutorialId": 1,
                                                            "name": "Transit",
                                                            "cardtitle": "Transportation",
                                                            "keyword": "Airport, Subway, Buses, Bike",
                                                            "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_1.png",
                                                            "isCompleted": true,
                                                            "completedAt": "2025-01-15T14:30:00"
                                                          },
                                                          {
                                                            "id": 2,
                                                            "memberId": 123,
                                                            "tutorialId": 2,
                                                            "name": "Dormitory",
                                                            "cardtitle": "Dormitory Life",
                                                            "keyword": "Room, Facilities, Rules",
                                                            "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_2.png",
                                                            "isCompleted": true,
                                                            "completedAt": "2025-01-15T14:30:00"
                                                          }
                                                        ]
                                                         """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<StampResponse>> getAllStamps();
}