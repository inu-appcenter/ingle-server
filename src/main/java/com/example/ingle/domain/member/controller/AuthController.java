package com.example.ingle.domain.member.controller;

import com.example.ingle.domain.member.service.AuthService;
import com.example.ingle.domain.member.dto.req.JwtTokenRequestDto;
import com.example.ingle.domain.member.dto.req.LoginRequestDto;
import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.dto.res.LoginResponseDto;
import com.example.ingle.global.jwt.MemberDetail;
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
    public ResponseEntity<LoginResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequestDto));
    }

    // 포털 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@Valid @RequestBody JwtTokenRequestDto jwtTokenRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(jwtTokenRequestDto));
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
    public ResponseEntity<String> loginTest(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginTest(loginRequestDto));
    }
}
