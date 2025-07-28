package com.example.ingle.domain.map;

import com.example.ingle.domain.map.dto.res.MapDetailResponseDto;
import com.example.ingle.domain.map.dto.res.MapResponseDto;
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
public class MapService {

    private final MapRepository mapRepository;

    @Transactional(readOnly = true)
    public List<MapResponseDto> findMapsInBounds(double minLat, double maxLat, double minLng, double maxLng, MapCategory category) {

        log.info("[지도 범위 조회]");

        List<Map> maps = mapRepository.findMapsInBounds(minLat, maxLat, minLng, maxLng, category);

        List<MapResponseDto> mapResponseDtos = maps.stream()
                        .map(map -> MapResponseDto.builder().map(map).build()).toList();

        log.info("[지도 범위 조회 성공]");

        return mapResponseDtos;
    }

    @Transactional(readOnly = true)
    public MapDetailResponseDto findById(Long mapId) {

        log.info("[건물 상세 조회]");

        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> {
                    log.warn("[건물 조회 실패] 존재하지 않는 건물 mapId: {}", mapId);
                    return new CustomException(ErrorCode.MAP_NOT_FOUND);
                });

        MapDetailResponseDto mapDetailResponseDto = MapDetailResponseDto.builder().map(map).build();

        log.info("[건물 상세 조회 성공]");

        return mapDetailResponseDto;
    }

    @Transactional(readOnly = true)
    public List<MapResponseDto> searchMaps(String keyword) {

        log.info("[지도 검색]");

        List<Map> maps = mapRepository.findByNameContainingOrderByCreatedAtDesc(keyword);

        List<MapResponseDto> mapResponseDtos = maps.stream()
                .map(map -> MapResponseDto.builder().map(map).build()).toList();

        log.info("[지도 검색 성공]");

        return mapResponseDtos;
    }
}
