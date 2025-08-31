package com.example.ingle.admin.member.controller;

import com.example.ingle.admin.member.dto.req.AdminMemberSearchRequest;
import com.example.ingle.admin.member.dto.res.AdminMemberCountResponse;
import com.example.ingle.admin.member.dto.res.AdminMemberResponse;
import com.example.ingle.admin.member.service.AdminMemberService;
import com.example.ingle.domain.member.domain.MemberDetail;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMemberController implements AdminMemberApiSpecification {

    private final AdminMemberService adminMemberService;

    // 회원 검색
    @GetMapping("/search")
    public ResponseEntity<Page<AdminMemberResponse>> searchMembers(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Valid @ParameterObject AdminMemberSearchRequest searchRequest,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        validateAdminRole(memberDetail);
        Long adminId = memberDetail.getMember().getId();

        log.info("[관리자 API 호출] 회원 검색 - adminId: {}", adminId);

        return ResponseEntity.ok(adminMemberService.searchMembers(searchRequest, pageable));
    }

    // 전체 회원 수 조회
    @GetMapping("/count")
    public ResponseEntity<AdminMemberCountResponse> getMemberCount(
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        validateAdminRole(memberDetail);
        Long adminId = memberDetail.getMember().getId();

        log.info("[관리자 API 호출] 회원 수 조회 - adminId: {}", adminId);

        return ResponseEntity.ok(adminMemberService.getMemberCount());
    }

    // 회원 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId
    ) {
        validateAdminRole(memberDetail);
        Long adminId = memberDetail.getMember().getId();

        // 자기 자신 삭제 방지
        if (adminId.equals(memberId)) {
            log.warn("[관리자 회원 삭제 실패] 자기 자신은 삭제할 수 없습니다: adminId={}", adminId);
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        log.info("[관리자 API 호출] 회원 삭제 - adminId: {}, targetMemberId: {}", adminId, memberId);

        adminMemberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 밴/언밴
    @PatchMapping("/{memberId}/ban")
    public ResponseEntity<AdminMemberResponse> banMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId,
            @RequestParam boolean ban
    ) {
        validateAdminRole(memberDetail);
        Long adminId = memberDetail.getMember().getId();

        // 자기 자신 밴 방지
        if (adminId.equals(memberId)) {
            log.warn("[관리자 회원 밴 실패] 자기 자신은 밴할 수 없습니다: adminId={}", adminId);
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        log.info("[관리자 API 호출] 회원 밴/언밴 - adminId: {}, targetMemberId: {}, ban: {}", adminId, memberId, ban);

        return ResponseEntity.ok(adminMemberService.banMember(memberId, ban));
    }

    /**
     * 관리자 권한 검증
     * 추가적인 보안 레이어로 컨트롤러에서도 한 번 더 체크
     */
    private void validateAdminRole(MemberDetail memberDetail) {
        if (memberDetail == null || memberDetail.getMember().getRole() != Role.ADMIN) {
            log.error("[관리자 권한 검증 실패] 관리자가 아닌 사용자의 접근 시도");
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }
}
