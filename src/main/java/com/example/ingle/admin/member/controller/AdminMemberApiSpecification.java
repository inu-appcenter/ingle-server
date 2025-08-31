package com.example.ingle.admin.member.controller;

import com.example.ingle.admin.member.dto.req.AdminMemberSearchRequest;
import com.example.ingle.admin.member.dto.res.AdminMemberCountResponse;
import com.example.ingle.admin.member.dto.res.AdminMemberResponse;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Admin - Member", description = "관리자 회원 관리 API")
@SecurityRequirement(name = "JWT")
public interface AdminMemberApiSpecification {

    @Operation(
            summary = "회원 검색",
            description = """
        회원을 검색합니다. 학번, 닉네임, 학과, 권한, 학생 유형, 국가로 검색 가능합니다.
        ADMIN 권한이 필요합니다.
        
        페이지네이션(Pageable) 사용 안내:
        - page (integer, query): 조회할 페이지 번호, 0부터 시작 (예: 0 = 첫 페이지)
        - size (integer, query): 한 페이지에 반환할 데이터 수 (기본값: 20)
        - sort (array[string], query): 정렬 기준, 'property,(asc|desc)' 형식
          - 예: sort=nickname,asc → 닉네임 오름차순 정렬
          - 예: sort=createdAt,desc&sort=nickname,asc → 생성일 내림차순, 그 다음 닉네임 오름차순 정렬
        
        예시 호출:
        GET /api/v1/admin/members/search?page=0&size=20&sort=nickname,asc
        """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 검색 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                    {
                      "content": [
                        {
                          "memberId": 1,
                          "studentId": "202301452",
                          "nickname": "test1",
                          "department": "COMPUTER_ENGINEERING",
                          "studentType": "EXCHANGE",
                          "country": "SOUTH_KOREA",
                          "role": "USER",
                          "imageUrl": "https://example.com/profile.jpg",
                          "createdAt": "2024-01-01T10:00:00",
                          "isBanned": false
                        }
                      ],
                      "pageable": {
                        "pageNumber": 0,
                        "pageSize": 20,
                        "sort": {
                          "sorted": false,
                          "unsorted": true,
                          "empty": true
                        }
                      },
                      "totalPages": 1,
                      "totalElements": 1
                    }
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
    ResponseEntity<Page<AdminMemberResponse>> searchMembers(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Valid @ParameterObject AdminMemberSearchRequest searchRequest,
            @ParameterObject Pageable pageable
    );

    @Operation(
            summary = "전체 회원 수 조회",
            description = "전체 회원 수와 활성/비활성 회원 수를 조회합니다.\n" +
                    "ADMIN 권한이 필요합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 수 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                              "totalCount": 150,
                                              "activeCount": 145,
                                              "bannedCount": 5
                                            }
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
    ResponseEntity<AdminMemberCountResponse> getMemberCount(
            @AuthenticationPrincipal MemberDetail memberDetail
    );

    @Operation(
            summary = "회원 삭제",
            description = "특정 회원을 삭제합니다.\n" +
                    "ADMIN 권한이 필요합니다.",
            parameters = {
                    @Parameter(name = "memberId", description = "회원 ID", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "회원 삭제 성공",
                            content = @Content
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "회원을 찾을 수 없습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                               "code": 404,
                                               "name": "MEMBER_NOT_FOUND",
                                               "message": "존재하지 않는 회원입니다.",
                                               "errors": null
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId
    );

    @Operation(
            summary = "회원 밴/언밴",
            description = "특정 회원을 밴 또는 언밴 처리합니다.\n" +
                    "ADMIN 권한이 필요합니다.",
            parameters = {
                    @Parameter(name = "memberId", description = "회원 ID", required = true),
                    @Parameter(name = "ban", description = "true: 밴, false: 언밴", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회원 밴/언밴 처리 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                              "memberId": 1,
                                              "studentId": "202301452",
                                              "nickname": "test1",
                                              "department": "COMPUTER_ENGINEERING",
                                              "studentType": "EXCHANGE",
                                              "country": "SOUTH_KOREA",
                                              "role": "BANNED",
                                              "imageUrl": "https://example.com/profile.jpg",
                                              "createdAt": "2024-01-01T10:00:00",
                                              "isBanned": true
                                            }
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "회원을 찾을 수 없습니다.",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                               "code": 404,
                                               "name": "MEMBER_NOT_FOUND",
                                               "message": "존재하지 않는 회원입니다.",
                                               "errors": null
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<AdminMemberResponse> banMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId,
            @Parameter(description = "true: 밴, false: 언밴") boolean ban
    );
}
