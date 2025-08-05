package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.dto.req.JwtTokenRequest;
import com.example.ingle.domain.member.dto.req.LoginRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.LoginResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.dto.res.SignupRequiredResponse;
import com.example.ingle.domain.member.entity.Member;
import com.example.ingle.domain.member.repository.INUMemberRepository;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.JwtProvider;
import com.example.ingle.global.jwt.RefreshToken;
import com.example.ingle.global.jwt.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final INUMemberRepository inuMemberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginSuccessResponse signup(MemberInfoRequest memberInfoRequest) {

        log.info("[회원가입 요청] studentId={}", memberInfoRequest.getStudentId());

        if (memberRepository.existsByStudentId(memberInfoRequest.getStudentId())) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        Member member = Member.fromSignupRequest(memberInfoRequest);
        memberRepository.save(member);

        // Spring Security 인증 없이 직접 JWT 발급
        LoginSuccessResponse token = jwtProvider.generateTokenFromMember(member);

        RefreshToken refreshToken = RefreshToken.builder()
                .member(member)
                .refreshToken(token.refreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        log.info("[회원가입 성공] studentId = {}", member.getStudentId());

        return token;
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {

        log.info("[로그인 요청] studentId={}", loginRequest.getStudentId());

        if (!inuMemberRepository.verifySchoolLogin(loginRequest.getStudentId(), loginRequest.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        Optional<Member> optionalMember = memberRepository.findByStudentId(loginRequest.getStudentId());
        if (optionalMember.isEmpty()) {
            log.info("[회원 가입 필요] studentId: {}", loginRequest.getStudentId());
            return new SignupRequiredResponse(loginRequest.getStudentId(), true, "회원 가입이 필요합니다.");
        }

        Member member = optionalMember.get();
        LoginSuccessResponse loginSuccessResponse = jwtProvider.authenticateAndGenerateToken(
                loginRequest.getStudentId(), member);

        log.info("[로그인 성공] studentId: {}", member.getStudentId());

        return loginSuccessResponse;
    }

    @Transactional
    public LoginSuccessResponse refresh(JwtTokenRequest jwtTokenRequest) {

        log.info("[JWT 토큰 재발급 요청]");

        if (!jwtProvider.validateToken(jwtTokenRequest.getRefreshToken(), "refresh")) {
            log.warn("[JWT 토큰 유효성 검증 실패] 만료된 Refresh Token");
            throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }

        Authentication authentication = jwtProvider.getAuthentication(jwtTokenRequest.getRefreshToken());
        String studentId = authentication.getName();
        log.info("[인증 정보 추출 완료] studentId={}", studentId);

        RefreshToken refreshToken = refreshTokenRepository.findByStudentId(authentication.getName())
                .orElseThrow(() -> {
                    log.warn("[RefreshToken 조회 실패] 저장된 토큰 없음 studentId: {}", studentId);
                    return new CustomException(ErrorCode.JWT_NOT_FOUND);
                });

        if (!refreshToken.getRefreshToken().equals(jwtTokenRequest.getRefreshToken())) {
            log.warn("[RefreshToken 불일치] 요청 토큰과 저장 토큰이 다름 studentId: {}", studentId);
            throw new CustomException(ErrorCode.JWT_NOT_MATCH);
        }

        LoginSuccessResponse loginSuccessResponse = jwtProvider.generateToken(authentication);
        log.info("[AccessToken/RefreshToken 재발급 완료] studentId: {}", studentId);

        RefreshToken newRefreshToken = refreshToken.updateValue(loginSuccessResponse.refreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return loginSuccessResponse;
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplicated(String nickname) {

        boolean exists = memberRepository.existsByNickname(nickname);

        log.info("[닉네임 중복 확인 결과] nickname={}, exists={}", nickname, exists);

        return exists;
    }

    @Transactional
    public void logout(Member member) {

        log.info("[로그아웃 요청] studentId: {}", member.getStudentId());

        RefreshToken refreshToken = refreshTokenRepository.findByStudentId(member.getStudentId())
                .orElseThrow(() -> {
                    log.warn("[로그아웃 실패] 저장된 RefreshToken 없음: studentId={}", member.getStudentId());
                    return new CustomException(ErrorCode.JWT_NOT_FOUND);
                });

        refreshTokenRepository.delete(refreshToken);
    }

    @Transactional
    public void deleteMember(Member member) {

        log.info("[회원 탈퇴 요청 시작] studentId: {}", member.getStudentId());

        Member memberToDelete = memberRepository.findByStudentId(member.getStudentId())
                .orElseThrow(() -> {
                    log.warn("[회원 정보 조회 실패] 사용자 없음: studentId={}", member.getStudentId());
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });

        refreshTokenRepository.deleteByStudentId(member.getStudentId());
        memberRepository.delete(memberToDelete);
    }

    @Transactional(readOnly = true)
    public String loginTest(LoginRequest loginRequest) {

        if (!inuMemberRepository.verifySchoolLogin(loginRequest.getStudentId(), loginRequest.getPassword())) {
            return "INU 포털 로그인 실패";
        }
        return "INU 포털 로그인 성공: " + loginRequest.getStudentId();
    }
}