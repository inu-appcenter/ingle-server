package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.dto.req.LoginRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.req.WithdrawalReasonRequest;
import com.example.ingle.domain.member.dto.res.LoginResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.service.AuthService;
import com.example.ingle.domain.member.domain.MemberDetail;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiSpecification{

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<LoginSuccessResponse> signup(@Valid @RequestBody MemberInfoRequest memberInfoRequest,
                                                       HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(memberInfoRequest, response));
    }

    // 포털 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                               HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(loginRequest, response);
        if (loginResponse instanceof LoginSuccessResponse)
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<LoginSuccessResponse> refresh(@CookieValue(value = "refreshToken", required = true) String refreshToken,
                                                        HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(refreshToken, response));
    }

    // 닉네임 중복 확인
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicated(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.checkNicknameDuplicated(nickname));
    }

    // 로그아웃
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal MemberDetail userDetails,
                                       HttpServletResponse response) {
        authService.logout(userDetails.getMember(), response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal MemberDetail userDetails,
                                             @Valid @RequestBody WithdrawalReasonRequest request,
                                             HttpServletResponse response) {
        authService.deleteMember(userDetails.getMember(), request, response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 포털 로그인 테스트
    @PostMapping("/login/test")
    public ResponseEntity<String> loginTest(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginTest(loginRequest));
    }
}
