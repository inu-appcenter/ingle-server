package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.dto.req.JwtTokenRequestDto;
import com.example.ingle.domain.member.dto.req.LoginRequestDto;
import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.dto.res.LoginResponseDto;
import com.example.ingle.domain.member.dto.res.SignupRequiredResponseDto;
import com.example.ingle.global.exception.ErrorResponseEntity;
import com.example.ingle.global.jwt.MemberDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원 인증 및 권한 관련 API")
public interface AuthApiSpecification {

    @Operation(
            summary = "회원가입",
            description = "회원가입을 진행합니다." +
                    "<br><br>요청 값에 로그인 시 입력했던 학번을 같이 담아서 보내주세요." +
                    "<br><br>닉네임은 3~20자 사이의 문자열만 허용합니다.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponseDto.class)
                            )
                    )
            }
    )
    ResponseEntity<LoginResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto);

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
                            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "202",
                            description = "회원가입 필요",
                            content = @Content(schema = @Schema(implementation = SignupRequiredResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(name = "MEMBER_NOT_FOUND", summary = "회원을 찾을 수 없습니다.",
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
                    @ApiResponse(responseCode = "401", description = "포털 로그인 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(name = "LOGIN_FAILED", summary = "로그인이 실패하였습니다.",
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
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @Operation(
            summary = "토큰 재발급",
            description = "Refresh Token을 재발급 합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "새로운 리프레시 토큰 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponseDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "420", description = "만료된 리프레시 토큰입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(name = "JWT_REFRESH_TOKEN_EXPIRED", summary = "[Jwt] 만료된 리프레시 토큰입니다.",
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
                                    examples = @ExampleObject(name = "JWT_NOT_FOUND", summary = "[Jwt] 리프레시 토큰 조회 실패",
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
                                    examples = @ExampleObject(name = "JWT_NOT_MATCH", summary = "[Jwt] 리프레시 토큰 불일치",
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
    ResponseEntity<LoginResponseDto> refresh(@Valid @RequestBody JwtTokenRequestDto jwtTokenRequestDto);

    @Operation(
            summary = "닉네임 중복 확인",
            description = "닉네임이 사용 가능한 지 확인합니다." +
                    "<br><br>사용 가능하면 true, 중복되면 400을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용 가능 여부 반환",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Boolean.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "닉네임이 중복되었습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(name = "NICKNAME_DUPLICATED", summary = "닉네임이 중복되었습니다.",
                                            value = """
                                                {
                                                   "code": 400,
                                                   "name": "NICKNAME_DUPLICATED",
                                                   "message": "닉네임이 중복되었습니다.",
                                                   "errors": null
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<Boolean> checkNicknameDuplicated(@PathVariable String nickname);

    @Operation(
            summary = "회원 탈퇴",
            description = "회원 탈퇴를 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
                    @ApiResponse(responseCode = "404", description = "멤버를 찾을 수 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class),
                                    examples = @ExampleObject(name = "MEMBER_NOT_FOUND", summary = "회원을 찾을 수 없습니다.",
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
            description = "포털 아이디와 비밀번호로 로그인을 테스트합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "테스트 성공",
                            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))
                    )
            }
    )
    ResponseEntity<String> loginTest(@Valid @RequestBody LoginRequestDto loginRequestDto);
}
