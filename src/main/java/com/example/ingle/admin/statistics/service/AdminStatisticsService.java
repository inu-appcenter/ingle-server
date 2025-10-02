package com.example.ingle.admin.statistics.service;

import com.example.ingle.admin.statistics.dto.AdminStampProgress;
import com.example.ingle.admin.statistics.dto.res.AdminStampProgressResponse;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.domain.stamp.repository.MemberStampRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStatisticsService {

    private final MemberRepository memberRepository;
    private final MemberStampRepository memberStampRepository;

    /*
    * 해당 스탬프를 획득한(튜토리얼을 완료한) 회원 수 조회
    */
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminStampProgressResponse> getStampProgress() {

        List<AdminStampProgress> progressStats = memberStampRepository.findStampProgress();
        long totalMemberCount = memberRepository.count();

        return progressStats.stream()
                .map(dto -> AdminStampProgressResponse.of(
                        dto.stampName(),
                        dto.acquiredCount(),
                        totalMemberCount
                ))
                .toList();
    }
}