package com.example.ingle.admin.statistics.controller;

import com.example.ingle.admin.statistics.dto.res.AdminStampProgressResponse;
import com.example.ingle.admin.statistics.service.AdminStatisticsService;
import com.example.ingle.domain.member.domain.MemberDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/statistics")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatisticsController {

    private final AdminStatisticsService adminStatisticsService;

    /**
     * 스탬프 획득률 조회
     * @param memberDetail
     * @return
     */
    @GetMapping("/stamps")
    public ResponseEntity<List<AdminStampProgressResponse>> getStampProgress(
            @AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.ok(adminStatisticsService.getStampProgress());
    }
}
