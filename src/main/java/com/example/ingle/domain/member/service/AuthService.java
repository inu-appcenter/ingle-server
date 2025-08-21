package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.dto.req.LoginRequest;
import com.example.ingle.domain.member.dto.req.MemberInfoRequest;
import com.example.ingle.domain.member.dto.res.LoginResponse;
import com.example.ingle.domain.member.dto.res.LoginSuccessResponse;
import com.example.ingle.domain.member.dto.res.SignupRequiredResponse;
import com.example.ingle.domain.member.repository.INUMemberRepository;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.JwtProvider;
import com.example.ingle.global.jwt.refreshToken.RefreshToken;
import com.example.ingle.global.jwt.refreshToken.RefreshTokenRepository;
import com.example.ingle.global.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MemberService memberService;

    @Transactional
    public LoginSuccessResponse signup(MemberInfoRequest memberInfoRequest, HttpServletResponse response) {
        validateMember(memberInfoRequest);

        Member member = Member.fromSignupRequest(memberInfoRequest);
        memberRepository.save(member);

        // Spring Security 인증 없이 직접 JWT 발급
        return jwtProvider.generateTokenFromMember(member, response);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        verifySchoolLogin(loginRequest);

        Optional<Member> member = memberRepository.findByStudentId(loginRequest.studentId());
        if (member.isEmpty()) {
            log.info("[회원 가입 필요] studentId: {}", loginRequest.studentId());
            return new SignupRequiredResponse(loginRequest.studentId(), true, "회원 가입이 필요합니다.");
        }

        return jwtProvider.authenticateAndGenerateToken(member.get(), response);
    }

    @Transactional
    public LoginSuccessResponse refresh(String refreshToken, HttpServletResponse response) {
        jwtProvider.validateRefreshTokenAndDelete(refreshToken);

        RefreshToken studentRefreshToken = jwtProvider.findRefreshToken(refreshToken);
        Member member = memberService.getMemberByStudentId(studentRefreshToken.getStudentId());

        jwtProvider.matchRefreshToken(studentRefreshToken.getRefreshToken(), refreshToken);

        refreshTokenRepository.delete(studentRefreshToken);

        return jwtProvider.generateTokenFromMember(member, response);
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplicated(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public void logout(Member member, HttpServletResponse response) {
        RefreshToken refreshToken = jwtProvider.findRefreshTokenByStudentId(member.getStudentId());

        refreshTokenRepository.delete(refreshToken);
        CookieUtil.deleteCookie(response, "refreshToken");
    }

    @Transactional
    public void deleteMember(Member member, HttpServletResponse response) {
        Member memberToDelete = memberService.getMemberByStudentId(member.getStudentId());

        refreshTokenRepository.deleteByStudentId(member.getStudentId());
        memberRepository.delete(memberToDelete);
        CookieUtil.deleteCookie(response, "refreshToken");
    }

    @Transactional(readOnly = true)
    public String loginTest(LoginRequest loginRequest) {
        verifySchoolLogin(loginRequest);
        return "INU 포털 로그인 성공: " + loginRequest.studentId();
    }

    private void validateMember(MemberInfoRequest memberInfoRequest) {
        if (memberRepository.existsByStudentId(memberInfoRequest.studentId())) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }
        if (memberRepository.existsByNickname(memberInfoRequest.nickname())) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }

    private void verifySchoolLogin(LoginRequest loginRequest) {
        if (!inuMemberRepository.verifySchoolLogin(loginRequest.studentId(), loginRequest.password())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
    }
}