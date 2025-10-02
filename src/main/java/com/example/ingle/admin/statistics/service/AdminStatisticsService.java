package com.example.ingle.admin.statistics.service;

import com.example.ingle.admin.member.dto.req.AdminMemberSearchRequest;
import com.example.ingle.admin.member.dto.res.AdminMemberCountResponse;
import com.example.ingle.admin.member.dto.res.AdminMemberResponse;
import com.example.ingle.admin.statistics.dto.res.AdminProgressResponse;
import com.example.ingle.domain.member.domain.Member;
import com.example.ingle.domain.member.enums.Role;
import com.example.ingle.domain.member.repository.MemberRepository;
import com.example.ingle.domain.stamp.entity.Stamp;
import com.example.ingle.domain.stamp.repository.MemberStampRepository;
import com.example.ingle.domain.stamp.repository.StampRepository;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import com.example.ingle.global.jwt.refreshToken.RefreshTokenRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStatisticsService {

    private final MemberRepository memberRepository;
    private final MemberStampRepository memberStampRepository;
    private final StampRepository stampRepository;

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminProgressResponse> getStampProgress() {

        List<Stamp> stamps = stampRepository.findAllByOrderById();
        long totalMemberCount = memberRepository.count();

        return stamps.stream()
                .map(stamp -> {
                    // 해당 스탬프를 획득한(튜토리얼을 완료한) 회원 수 조회
                    long acquiredCount = memberStampRepository.countByTutorialId(stamp.getTutorialId());

                    return AdminProgressResponse.builder()
                            .stampName(stamp.getName())
                            .acquiredCount(acquiredCount)
                            .totalCount(totalMemberCount)
                            .build();
                })
                .toList();
    }
}