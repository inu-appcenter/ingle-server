package com.example.ingle.domain.member.service;

import com.example.ingle.domain.member.Member;
import com.example.ingle.domain.member.dto.req.JwtTokenRequestDto;
import com.example.ingle.domain.member.dto.req.LoginRequestDto;
import com.example.ingle.domain.member.dto.req.SignupRequestDto;
import com.example.ingle.domain.member.dto.res.LoginResponseDto;
import com.example.ingle.domain.member.dto.res.SignupRequiredResponseDto;
import com.example.ingle.domain.member.repository.INUMemberRepository;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.JwtTokenProvider;
import com.example.ingle.global.jwt.RefreshToken;
import com.example.ingle.global.jwt.RefreshTokenRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final Optional<INUMemberRepository> inuMemberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponseDto signup(SignupRequestDto signupRequestDto) {

        log.info("[회원가입 요청] studentId={}", signupRequestDto.getStudentId());

        if (memberRepository.existsByStudentId(signupRequestDto.getStudentId())) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        Member member = new Member(signupRequestDto);
        memberRepository.save(member);

        log.info("[회원가입 완료] memberId: {}, studentId: {}, nickname: {}",
                member.getId(),member.getStudentId(), member.getNickname());

        // Spring Security 인증 없이 직접 JWT 발급
        LoginResponseDto token = jwtTokenProvider.generateTokenFromMember(member);

        RefreshToken refreshToken = RefreshToken.builder()
                .member(member)
                .refreshToken(token.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        log.info("[회원가입 성공] studentId = {}", member.getStudentId());

        return token;
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        log.info("[로그인 요청] studentId={}", loginRequestDto.getStudentId());

        if (inuMemberRepository.isPresent()) {
            if (!inuMemberRepository.get().verifySchoolLogin(loginRequestDto.getStudentId(), loginRequestDto.getPassword())) {
                throw new CustomException(ErrorCode.LOGIN_FAILED);
            }
        }

        if (!memberRepository.existsByStudentId(loginRequestDto.getStudentId())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SignupRequiredResponseDto(loginRequestDto.getStudentId()));
        }

        Member member = memberRepository.findByStudentId(loginRequestDto.getStudentId())
                .orElseThrow(() -> {
                    log.warn("[로그인 실패] 사용자 없음: studentId={}", loginRequestDto.getStudentId());
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });

        LoginResponseDto loginResponseDto = jwtTokenProvider.authenticateAndGenerateToken(
                loginRequestDto.getStudentId(), member);

        log.info("[로그인 성공] studentId: {}", member.getStudentId());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @Transactional
    public LoginResponseDto refresh(JwtTokenRequestDto jwtTokenRequestDto) {

        log.info("[JWT 토큰 재발급 요청]");

        if (!jwtTokenProvider.validateToken(jwtTokenRequestDto.getRefreshToken(), "refresh")) {
            log.warn("[JWT 토큰 유효성 검증 실패] 만료된 Refresh Token");
            throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenRequestDto.getRefreshToken());
        String studentId = authentication.getName();
        log.info("[인증 정보 추출 완료] studentId={}", studentId);

        RefreshToken refreshToken = refreshTokenRepository.findByStudentId(authentication.getName())
                .orElseThrow(() -> {
                    log.warn("[RefreshToken 조회 실패] 저장된 토큰 없음 studentId: {}", studentId);
                    return new CustomException(ErrorCode.JWT_NOT_FOUND);
                });

        if (!refreshToken.getRefreshToken().equals(jwtTokenRequestDto.getRefreshToken())) {
            log.warn("[RefreshToken 불일치] 요청 토큰과 저장 토큰이 다름 studentId: {}", studentId);
            throw new CustomException(ErrorCode.JWT_NOT_MATCH);
        }

        log.info("[RefreshToken 일치 확인 완료] 새 AccessToken 및 RefreshToken 생성 시작");

        LoginResponseDto loginResponseDto = jwtTokenProvider.generateToken(authentication);
        log.info("[AccessToken/RefreshToken 재발급 완료] studentId: {}", studentId);

        RefreshToken newRefreshToken = refreshToken.updateValue(loginResponseDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        log.info("[새 RefreshToken 저장 완료] studentId: {}", studentId);

        return loginResponseDto;
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplicated(String nickname) {

        log.info("[닉네임 중복 확인] nickname={}", nickname);

        if (memberRepository.existsByNickname(nickname)) {
            log.warn("[중복 닉네임] nickname={}", nickname);
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATED);
        }
        return true;
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

        log.info("[로그아웃 성공] studentId: {}", member.getStudentId());
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

        log.info("[회원 탈퇴 완료] studentId: {}", memberToDelete.getStudentId());
    }

    @Transactional(readOnly = true)
    public String loginTest(@Valid LoginRequestDto loginRequestDto) {

        log.info("[로그인 테스트 요청] studentId={}", loginRequestDto.getStudentId());

        if (inuMemberRepository.isPresent()) {
            if (!inuMemberRepository.get().verifySchoolLogin(loginRequestDto.getStudentId(), loginRequestDto.getPassword())) {
                return "INU 포털 로그인 실패";
            }
        }

        return "INU 포털 로그인 성공: " + loginRequestDto.getStudentId();
    }
}
