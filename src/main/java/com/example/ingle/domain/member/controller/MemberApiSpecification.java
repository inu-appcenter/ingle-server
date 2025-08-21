package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.MyPageResponse;
import com.example.ingle.global.exception.ErrorResponseEntity;
import com.example.ingle.domain.member.entity.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Member", description = "마이페이지 관련 API")
public interface MemberApiSpecification {

    @Operation(
            summary = "마이페이지 조회",
            description = "로그인 된 사용자의 마이페이지 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마이페이지 조회 성공",
                            content = @Content(schema = @Schema(implementation = MyPageResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "MEMBER_NOT_FOUND",
                                                   "message": "회원을 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<MyPageResponse> getMyPage(@AuthenticationPrincipal MemberDetail memberDetail);

    @Operation(
            summary = "마이페이지 수정",
            description = "로그인 된 사용자의 마이페이지 정보를 수정합니다." +
                    "<br><br>입력되지 않은 필드는 수정되지 않습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "마이페이지 수정 성공",
                            content = @Content(schema = @Schema(implementation = MyPageResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "MEMBER_NOT_FOUND",
                                                   "message": "회원을 찾을 수 없습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<MyPageResponse> updateMyPage(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Valid @RequestBody MemberInfoRequest memberInfoRequest);
}
