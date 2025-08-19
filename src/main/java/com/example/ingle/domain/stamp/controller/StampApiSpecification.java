package com.example.ingle.domain.stamp.controller;

import com.example.ingle.domain.stamp.res.CompleteTutorialResponse;
import com.example.ingle.domain.stamp.res.ProgressResponse;
import com.example.ingle.domain.stamp.res.StampResponse;
import com.example.ingle.global.exception.ErrorResponseEntity;
import com.example.ingle.global.jwt.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Stamp", description = "스탬프 관련 API")
@SecurityRequirement(name = "JWT")
public interface StampApiSpecification {

    @Operation(
            summary = "내 특정 스탬프 조회",
            description = "로그인한 사용자의 특정 스탬프 정보를 조회합니다.",
            parameters = {
                    @Parameter(name = "stampId", description = "스탬프 ID", required = true, example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "스탬프 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StampResponse.class),
                            examples = @ExampleObject(
                                    name = "스탬프 조회 응답 예시",
                                    value = """
                                              {
                                                "id": 1,
                                                "tutorialId": 1,
                                                "name": "Transit",
                                                "cardTitle": "Transportation",
                                                "keyword": "Airport, Subway, Buses, Bike",
                                                "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_1.png",
                                                "completedAt": "2025-01-15T14:30:00"
                                              }
                                             """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 401,
                                               "name": "UNAUTHORIZED",
                                               "message": "인증이 필요합니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "스탬프를 찾을 수 없습니다.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    name = "스탬프를 찾을 수 없는 경우",
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
    })
    ResponseEntity<StampResponse> getStamp(Long stampId, MemberDetail memberDetail);


    @Operation(
            summary = "내 전체 스탬프 목록 조회",
            description = "로그인한 사용자의 모든 스탬프를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "전체 스탬프 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StampResponse.class)),
                            examples = @ExampleObject(
                                    name = "전체 스탬프 목록 응답 예시",
                                    value = """
                                                        [
                                                          {
                                                            "id": 1,
                                                            "tutorialId": 1,
                                                            "name": "Transit",
                                                            "cardTitle": "Transportation",
                                                            "keyword": "Airport, Subway, Buses, Bike",
                                                            "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_1.png",
                                                            "completedAt": "2025-01-15T14:30:00"
                                                          },
                                                          {
                                                            "id": 2,
                                                            "tutorialId": 2,
                                                            "name": "Dormitory",
                                                            "cardTitle": "Dormitory Life",
                                                            "keyword": "Room, Facilities, Rules",
                                                            "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_2.png",
                                                            "completedAt": null
                                                          },
                                                          {
                                                            "id": 3,
                                                            "tutorialId": 3,
                                                            "name": "Campus",
                                                            "cardTitle": "Campus Life",
                                                            "keyword": "Library, Cafeteria, Study Room",
                                                            "stampImageUrl": "https://ingle-server.inuappcenter.kr/images/stamp_3.png",
                                                            "completedAt": "2025-01-16T09:15:00"
                                                          }
                                                        ]
                                                         """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 401,
                                               "name": "UNAUTHORIZED",
                                               "message": "인증이 필요합니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<List<StampResponse>> getAllStamps(MemberDetail memberDetail);

    @Operation(
            summary = "튜토리얼 완료 처리",
            description = "로그인한 사용자의 튜토리얼 완료를 처리하고 스탬프를 획득합니다.",
            parameters = {
                    @Parameter(name = "tutorialId", description = "튜토리얼 ID", required = true, example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "튜토리얼 완료 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CompleteTutorialResponse.class),
                            examples = @ExampleObject(
                                    name = "튜토리얼 완료 처리 응답 예시",
                                    value = """
                                            {
                                              "id": 1,
                                              "tutorialId": 1,
                                              "completedAt": "2025-01-15T14:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 401,
                                               "name": "UNAUTHORIZED",
                                               "message": "인증이 필요합니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 튜토리얼",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    name = "튜토리얼을 찾을 수 없는 경우",
                                    value = """
                                            {
                                               "code": 404,
                                               "name": "TUTORIAL_NOT_FOUND",
                                               "message": "튜토리얼을 찾을 수 없습니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 완료된 튜토리얼",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 409,
                                               "name": "TUTORIAL_ALREADY_COMPLETED",
                                               "message": "이미 완료된 튜토리얼입니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<CompleteTutorialResponse> completeTutorial(Long tutorialId, MemberDetail memberDetail);


    @Operation(
            summary = "내 진행률 조회",
            description = "로그인한 사용자의 튜토리얼 완료 진행률을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "진행률 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProgressResponse.class),
                            examples = @ExampleObject(
                                    name = "진행률 조회 응답 예시",
                                    value = """
                                            {
                                              "memberId": 123,
                                              "completedCount": 3,
                                              "totalCount": 13
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 401,
                                               "name": "UNAUTHORIZED",
                                               "message": "인증이 필요합니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ProgressResponse> getProgress(MemberDetail memberDetail);
}
