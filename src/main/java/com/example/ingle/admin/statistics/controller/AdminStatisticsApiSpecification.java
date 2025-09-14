package com.example.ingle.admin.statistics.controller;

import com.example.ingle.admin.statistics.dto.res.AdminProgressResponse;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "Admin - Statistics", description = "관리자 통계 API")
@SecurityRequirement(name = "JWT")
public interface AdminStatisticsApiSpecification {

    @Operation(
            summary = "스탬프 획득률 조회",
            description = "모든 스탬프(튜토리얼)별 획득한 회원 수와 전체 회원 수를 조회합니다.\n" +
                    "ADMIN 권한이 필요합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스탬프 획득률 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AdminProgressResponse.class)),
                                    examples = @ExampleObject(
                                            value = """
                                    [
                                      {
                                        "stampName": "Transit",
                                        "aquiredCount": 145,
                                        "totalCount": 150
                                      },
                                      {
                                        "stampName": "Dormitory",
                                        "aquiredCount": 130,
                                        "totalCount": 150
                                      },
                                      {
                                        "stampName": "Festival",
                                        "aquiredCount": 120,
                                        "totalCount": 150
                                      }
                                    ]
                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "권한이 없습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                    {
                                       "code": 403,
                                       "name": "ACCESS_DENIED",
                                       "message": "관리자 권한이 필요합니다.",
                                       "errors": null
                                    }
                                    """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<AdminProgressResponse>> getStampProgress(
            @AuthenticationPrincipal MemberDetail memberDetail
    );
}
