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

        Specification<Member> spec = createSearchSpecification(searchRequest);
        Page<Member> members = memberRepository.findAll(spec, pageable);

        return members.map(AdminMemberResponse::from);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public AdminMemberCountResponse getMemberCount() {

        long totalCount = memberRepository.count();
        long bannedCount = memberRepository.countByRole(Role.BANNED);

        return AdminMemberCountResponse.builder()
                .totalCount(totalCount)
                .bannedCount(bannedCount)
                .activeCount(totalCount - bannedCount)
                .build();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(Long adminId, Long memberId) {
        validateAdminPermission(adminId, memberId);
        Member member = getMemberById(memberId);

        try {
            // 관련 데이터 삭제
            memberStampRepository.deleteAllByMemberId(memberId);
            refreshTokenRepository.deleteByStudentId(member.getStudentId());

            // 회원 삭제
            memberRepository.delete(member);

            log.info("[관리자 회원 삭제 완료] memberId={}", memberId);
        } catch (Exception e) {
            log.error("[관리자 회원 삭제 실패] memberId={}, error={}", memberId, e.getMessage());
            throw new CustomException(ErrorCode.MEMBER_DELETE_FAILED);
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public AdminMemberResponse banMember(Long adminId, Long memberId, boolean ban) {
        validateAdminPermission(adminId, memberId);
        Member member = getMemberById(memberId);

        if (ban) {
            if (member.getRole() != Role.BANNED) {
                member.banMember();
                refreshTokenRepository.deleteByStudentId(member.getStudentId());
                log.info("[회원 밴 처리] memberId={}", memberId);
            }
        } else {
            if (member.getRole() == Role.BANNED) {
                member.unbanMember();
                log.info("[회원 언밴 처리] memberId={}", memberId);
            }
        }

        return AdminMemberResponse.from(member);
    }

    // 본인 삭제, 밴 방지
    private void validateAdminPermission(Long adminId, Long targetMemberId) {
        if (adminId.equals(targetMemberId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        Member targetMember = getMemberById(targetMemberId);
        if (targetMember.getRole() == Role.ADMIN) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
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