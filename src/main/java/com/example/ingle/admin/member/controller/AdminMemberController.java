package com.example.ingle.admin.member.controller;

import com.example.ingle.admin.member.dto.req.AdminMemberSearchRequest;
import com.example.ingle.admin.member.dto.res.AdminMemberCountResponse;
import com.example.ingle.admin.member.dto.res.AdminMemberResponse;
import com.example.ingle.admin.member.service.AdminMemberService;
import com.example.ingle.domain.member.domain.MemberDetail;
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
    @GetMapping
    public ResponseEntity<Page<AdminMemberResponse>> searchMembers(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @Valid @ParameterObject AdminMemberSearchRequest searchRequest,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(adminMemberService.searchMembers(searchRequest, pageable));
    }

    // 전체 회원 수 조회
    @GetMapping("/count")
    public ResponseEntity<AdminMemberCountResponse> getMemberCount(
            @AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.ok(adminMemberService.getMemberCount());
    }

    // 회원 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId) {
        Long adminId = memberDetail.getMember().getId();
        adminMemberService.deleteMember(adminId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 밴/언밴
    @PatchMapping("/{memberId}/status")
    public ResponseEntity<AdminMemberResponse> banMember(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable Long memberId,
            @RequestParam boolean ban) {
        Long adminId = memberDetail.getMember().getId();
        return ResponseEntity.ok(adminMemberService.banMember(adminId, memberId, ban));
    }
}
