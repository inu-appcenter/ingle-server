package com.example.ingle.admin.member.service;

import com.example.ingle.admin.member.dto.req.AdminMemberSearchRequest;
import com.example.ingle.admin.member.dto.res.AdminMemberCountResponse;
import com.example.ingle.admin.member.dto.res.AdminMemberResponse;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.domain.stamp.repository.MemberStampRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final MemberStampRepository memberStampRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdminMemberResponse> searchMembers(AdminMemberSearchRequest searchRequest, Pageable pageable) {
        log.info("[관리자 회원 검색] 검색 조건: studentId={}, nickname={}",
                searchRequest.studentId(), searchRequest.nickname());

        Specification<Member> spec = createSearchSpecification(searchRequest);
        Page<Member> members = memberRepository.findAll(spec, pageable);

        return members.map(AdminMemberResponse::from);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public AdminMemberCountResponse getMemberCount() {
        log.info("[관리자 회원 수 조회]");

        long totalCount = memberRepository.count();
        long bannedCount = memberRepository.countByRole(Role.BANNED);
        long activeCount = totalCount - bannedCount;  // BANNED 제외한 모든 회원

        return AdminMemberCountResponse.builder()
                .totalCount(totalCount)
                .activeCount(activeCount)
                .bannedCount(bannedCount)
                .build();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(Long memberId) {
        log.info("[관리자 회원 삭제 시작] memberId={}", memberId);

        Member member = getMemberById(memberId);

        // 관리자 계정 삭제 방지
        if (member.getRole() == Role.ADMIN) {
            log.warn("[관리자 회원 삭제 실패] 관리자 계정은 삭제할 수 없습니다: memberId={}", memberId);
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        // 1. member_stamp 테이블에서 해당 회원의 스탬프 기록 삭제
        memberStampRepository.deleteAllByMemberId(memberId);
        log.info("[관리자 회원 삭제] 스탬프 기록 삭제 완료: memberId={}", memberId);

        // 2. refresh_token 테이블에서 토큰 삭제
        refreshTokenRepository.deleteByStudentId(member.getStudentId());
        log.info("[관리자 회원 삭제] 리프레시 토큰 삭제 완료: studentId={}", member.getStudentId());

        // 3. 회원 삭제
        memberRepository.delete(member);
        log.info("[관리자 회원 삭제] 회원 삭제 완료: memberId={}, studentId={}", memberId, member.getStudentId());
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminMemberResponse banMember(Long memberId, boolean ban) {
        log.info("[관리자 회원 밴/언밴 시작] memberId={}, ban={}", memberId, ban);

        Member member = getMemberById(memberId);

        // 관리자 계정 밴 방지
        if (member.getRole() == Role.ADMIN) {
            log.warn("[관리자 회원 밴 실패] 관리자 계정은 밴할 수 없습니다: memberId={}", memberId);
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if (ban) {
            // 이미 밴된 상태인지 확인
            if (member.getRole() == Role.BANNED) {
                log.warn("[관리자 회원 밴] 이미 밴된 회원: memberId={}", memberId);
                return AdminMemberResponse.from(member);
            }

            member.banMember();

            // 밴 처리 시 리프레시 토큰 삭제 (강제 로그아웃)
            refreshTokenRepository.deleteByStudentId(member.getStudentId());
            log.info("[관리자 회원 밴] memberId={}, studentId={}", memberId, member.getStudentId());
        } else {
            // 밴 상태가 아닌 경우
            if (member.getRole() != Role.BANNED) {
                log.warn("[관리자 회원 언밴] 밴 상태가 아닌 회원: memberId={}", memberId);
                return AdminMemberResponse.from(member);
            }

            member.unbanMember();
            log.info("[관리자 회원 언밴] memberId={}, studentId={}", memberId, member.getStudentId());
        }

        return AdminMemberResponse.from(member);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn("[관리자 회원 조회 실패] 존재하지 않는 회원: memberId={}", memberId);
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
    }

    private Specification<Member> createSearchSpecification(AdminMemberSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 학번 검색
            if (request.studentId() != null && !request.studentId().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        root.get("studentId"),
                        "%" + request.studentId() + "%"
                ));
            }

            // 닉네임 검색
            if (request.nickname() != null && !request.nickname().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nickname")),
                        "%" + request.nickname().toLowerCase() + "%"
                ));
            }

            // 학과 검색
            if (request.department() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("department"),
                        request.department()
                ));
            }

            // 권한(Role) 검색 - 밴 상태 확인용
            if (request.role() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("role"),
                        request.role()
                ));
            }

            // 학생 유형 검색
            if (request.studentType() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("studentType"),
                        request.studentType()
                ));
            }

            // 국가 검색
            if (request.country() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("country"),
                        request.country()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}