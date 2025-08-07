package com.example.ingle.domain.memberreward.controller;

import com.example.ingle.domain.memberreward.dto.res.CompleteTutorialResponseDto;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardProgressResponseDto;
import com.example.ingle.domain.memberreward.dto.res.MemberRewardStatusResponseDto;
import com.example.ingle.global.exception.ErrorResponseEntity;
import com.example.ingle.global.jwt.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Member-Reward", description = "개인 리워드 관련 API")
public interface MemberRewardApiSpecification {

    @Operation(
            summary = "튜토리얼 완료 처리",
            description = "사용자가 튜토리얼을 완료했을 때 기록을 저장하고 리워드를 획득합니다." +
                    "<br><br>이미 완료한 튜토리얼은 중복 완료할 수 없습니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "튜토리얼 완료 처리 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CompleteTutorialResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "이미 완료한 튜토리얼입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 400,
                                                   "name": "TUTORIAL_ALREADY_COMPLETED",
                                                   "message": "이미 완료한 튜토리얼입니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 튜토리얼입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "TUTORIAL_NOT_FOUND",
                                                   "message": "존재하지 않는 튜토리얼입니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<CompleteTutorialResponseDto> completeTutorial(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable("tutorialId") Long tutorialId);

    @Operation(
            summary = "리워드 상태 조회 (리워드 위치 기준)",
            description = "특정 리워드 위치의 리워드 획득 상태를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리워드 상태 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberRewardStatusResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 리워드 위치입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "REWARD_POSITION_NOT_FOUND",
                                                   "message": "존재하지 않는 리워드 위치입니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<MemberRewardStatusResponseDto> getRewardStatusByPosition(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable("position") Integer rewardPosition);

    @Operation(
            summary = "멤버 튜토리얼 진행률 조회",
            description = "현재 로그인한 사용자의 튜토리얼 완료 진행률을 조회합니다." +
                    "<br><br>완료된 튜토리얼 수와 전체 튜토리얼 수를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "진행률 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberRewardProgressResponseDto.class),
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
                    )
            }
    )
    ResponseEntity<MemberRewardProgressResponseDto> getProgress(
            @AuthenticationPrincipal MemberDetail memberDetail);
}
