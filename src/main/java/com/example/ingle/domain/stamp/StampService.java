package com.example.ingle.domain.stamp;

import com.example.ingle.domain.stamp.res.StampResponse;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;

    // 스탬프 상세 조회 (기존 기본조회 + 상세조회 통합)
    @Transactional(readOnly = true)
    public StampResponse getStamp(Long id) {

        Stamp stamp = stampRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[스탬프 조회 실패] 존재하지 않는 스탬프: {}", id);
                    return new CustomException(ErrorCode.STAMP_NOT_FOUND);
                });

        return StampResponse.builder().stamp(stamp).build();
    }

    // 전체 스탬프 목록 조회 (ID순, 이름만)
    @Transactional(readOnly = true)
    public List<StampResponse> getAllStamps() {

        List<Stamp> stamps = stampRepository.findAllByOrderById();
        List<StampResponse> stampResponse = stamps.stream()
                .map(stamp -> StampResponse.builder().stamp(stamp).build())
                .toList();

        return stampResponse;
    }
}
