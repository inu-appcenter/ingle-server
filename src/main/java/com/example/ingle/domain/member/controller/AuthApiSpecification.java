package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.dto.req.JwtTokenRequest;
import com.example.ingle.domain.member.dto.req.LoginRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.LoginResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.dto.res.SignupRequiredResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth", description = "회원 인증 및 권한 관련 API")
public interface AuthApiSpecification {

    @Operation(
            summary = "회원가입",
            description = "회원가입을 진행합니다." +
                    "<br><br>닉네임은 3~20자 사이의 문자열만 허용합니다.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginSuccessResponse.class)
                            )
                    )
            }
    )
    ResponseEntity<LoginSuccessResponse> signup(@Valid @RequestBody MemberInfoRequest memberInfoRequest);

    @Operation(
            summary = "INU 포털 로그인",
            description = "포털 아이디와 비밀번호로 로그인합니다." +
                    "<br><br>회원가입이 필요한 경우 Status Code로 202를 응답합니다.  " +
                    "<br>회원가입 시에는 회원가입 API 호출이 필요합니다." +
                    "<br><br> accessToken : 2시간 / refreshToken : 3일",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(schema = @Schema(implementation = LoginSuccessResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "202",
                            description = "회원가입 필요",
                            content = @Content(schema = @Schema(implementation = SignupRequiredResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "포털 로그인 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 401,
                                                   "name": "LOGIN_FAILED",
                                                   "message": "로그인이 실패하였습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest);

    @Operation(
            summary = "토큰 재발급",
            description = "Refresh Token을 재발급 합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "새로운 리프레시 토큰 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginSuccessResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "420", description = "만료된 리프레시 토큰입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 420,
                                                   "name": "JWT_REFRESH_TOKEN_EXPIRED",
                                                   "message": "[Jwt] 만료된 리프레시 토큰입니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "리프레시 토큰 조회 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "JWT_NOT_FOUND",
                                                   "message": "[Jwt] 리프레시 토큰 조회 실패",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "리프레시 토큰 불일치",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 400,
                                                   "name": "JWT_NOT_MATCH",
                                                   "message": "[Jwt] 리프레시 토큰 불일치",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<LoginSuccessResponse> refresh(@Valid @RequestBody JwtTokenRequest jwtTokenRequest);

    @Operation(
            summary = "닉네임 중복 확인",
            description = "닉네임의 중복 여부를 확인합니다." +
                    "<br><br>true -> 중복, false -> 사용 가능",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 중복 확인",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    )
            }
    )
    ResponseEntity<Boolean> checkNicknameDuplicated(@RequestParam String nickname);

    @Operation(
            summary = "로그아웃",
            description = "로그인된 사용자의 로그아웃을 수행합니다." +
                    "<br><br>서버에서는 리프레시 토큰을 삭제하여 로그아웃 처리합니다." +
                    "<br><br>프론트엔드에서는 액세스 토큰을 삭제해야 합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
                    @ApiResponse(responseCode = "404", description = "[Jwt] 리프레시 토큰 조회 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                   "code": 404,
                                                   "name": "JWT_NOT_FOUND",
                                                   "message": "[Jwt] 리프레시 토큰 조회 실패",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    ),
            }
    )
    ResponseEntity<Void> logout(@AuthenticationPrincipal MemberDetail userDetails);

    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴를 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없습니다.",
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
                    ),
            }
    )
    ResponseEntity<Void> deleteMember(@AuthenticationPrincipal MemberDetail userDetails);

    @Operation(
            summary = "(TEST) INU 포털 로그인 테스트",
            description = "INU 포털 로그인 기능을 테스트합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "테스트 성공",
                            content = @Content(schema = @Schema(implementation = LoginSuccessResponse.class))
                    )
            }
    )
    ResponseEntity<String> loginTest(@Valid @RequestBody LoginRequest loginRequest);
}
