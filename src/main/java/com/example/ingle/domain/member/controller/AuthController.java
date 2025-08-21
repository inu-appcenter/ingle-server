package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.dto.req.JwtTokenRequest;
import com.example.ingle.domain.member.dto.req.LoginRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.LoginResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.service.AuthService;
import com.example.ingle.domain.member.entity.MemberDetail;
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
    public ResponseEntity<LoginSuccessResponse> signup(@Valid @RequestBody MemberInfoRequest memberInfoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(memberInfoRequest));
    }

    // 포털 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        if (loginResponse instanceof LoginSuccessResponse)
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<LoginSuccessResponse> refresh(@Valid @RequestBody JwtTokenRequest jwtTokenRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(jwtTokenRequest));
    }

    // 닉네임 중복 확인
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicated(@RequestParam String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.checkNicknameDuplicated(nickname));
    }

    // 로그아웃
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal MemberDetail userDetails) {
        authService.logout(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal MemberDetail userDetails) {
        authService.deleteMember(userDetails.getMember());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 포털 로그인 테스트
    @PostMapping("/login/test")
    public ResponseEntity<String> loginTest(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginTest(loginRequest));
    }
}
