package com.example.ingle.domain.progress.controller;

import com.example.ingle.domain.progress.res.CompleteTutorialResponse;
import com.example.ingle.domain.progress.res.ProgressResponse;
import com.example.ingle.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Progress", description = "튜토리얼 진행 상황 관련 API")
public interface ProgressApiSpecification {

    @Operation(
            summary = "튜토리얼 완료 처리",
            description = "특정 사용자의 튜토리얼 완료를 처리하고 진행 상황을 저장합니다."
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
                                              "tutorialId": 1,
                                              "stampId": 1,
                                              "completedAt": "2025-01-15T14:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 400,
                                               "name": "BAD_REQUEST",
                                               "message": "잘못된 요청입니다.",
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
    ResponseEntity<CompleteTutorialResponse> completeTutorial(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @RequestParam Long memberId,

            @Parameter(description = "튜토리얼 ID", required = true, example = "1")
            @PathVariable Long tutorialId
    );

    @Operation(
            summary = "사용자 진행률 조회",
            description = "특정 사용자의 튜토리얼 완료 진행률을 조회합니다."
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
                                              "completedCount": 3,
                                              "totalCount": 13
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 400,
                                               "name": "BAD_REQUEST",
                                               "message": "잘못된 요청입니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 사용자",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseEntity.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                               "code": 404,
                                               "name": "MEMBER_NOT_FOUND",
                                               "message": "사용자를 찾을 수 없습니다.",
                                               "errors": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<ProgressResponse> getProgressByMemberId(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable Long memberId
    );
}