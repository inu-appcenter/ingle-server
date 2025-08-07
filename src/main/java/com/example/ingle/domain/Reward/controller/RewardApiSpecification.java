package com.example.ingle.domain.reward.controller;

import com.example.ingle.domain.reward.dto.res.RewardCardResponseDto;
import com.example.ingle.domain.reward.dto.res.RewardResponseDto;
import com.example.ingle.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Reward", description = "리워드 관련 API")
public interface RewardApiSpecification {

    @Operation(
            summary = "리워드 조회",
            description = "위치별 리워드의 기본 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리워드 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RewardResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "리워드를 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "REWARD_NOT_FOUND",
                                                   "message": "리워드를 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<RewardResponseDto> getReward(@PathVariable @Min(1) Integer position);

    @Operation(
            summary = "리워드 카드 상세 조회",
            description = "위치별 리워드 카드의 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리워드 카드 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RewardCardResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "리워드를 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "REWARD_NOT_FOUND",
                                                   "message": "리워드를 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<RewardCardResponseDto> getRewardCard(@PathVariable("position") @Min(1) Integer position);

    @Operation(
            summary = "전체 리워드 목록 조회",
            description = "모든 리워드를 위치 순으로 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 리워드 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RewardResponseDto.class)),
                                            examples = @ExampleObject(
                                                    name = "전체 리워드 목록 응답 예시",
                                                    value = """
                                                        [
                                                          {
                                                            "id": 1,
                                                            "position": 1,
                                                            "name": "Transit",
                                                            "rewardImageUrl": "https://ingle-server.inuappcenter.kr/images/reward_1.png"
                                                          },
                                                          {
                                                            "id": 2,
                                                            "position": 2,
                                                            "name": "Dormitory",
                                                            "rewardImageUrl": "https://ingle-server.inuappcenter.kr/images/reward_2.png"
                                                          },
                                                          {
                                                            "id": 3,
                                                            "position": 3,
                                                            "name": "Library",
                                                            "rewardImageUrl": "https://ingle-server.inuappcenter.kr/images/reward_3.png"
                                                          }
                                                        ]
                                                         """
                                            )
                            )
                    )
            }
    )
    ResponseEntity<List<RewardResponseDto>> getAllRewards();
}