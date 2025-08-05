package com.example.ingle.domain.building.service;

import com.example.ingle.domain.building.entity.Building;
import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.repository.BuildingRepository;
import com.example.ingle.domain.building.dto.res.BuildingDetailResponse;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
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
public class BuildingService {

    private final BuildingRepository buildingRepository;

    @Transactional(readOnly = true)
    public List<BuildingResponse> findMapsInBounds(double minLat, double maxLat, double minLng, double maxLng, BuildingCategory buildingCategory) {

        log.info("[지도 범위 조회]");

        List<Building> buildings = buildingRepository.findMapsInBounds(minLat, maxLat, minLng, maxLng, buildingCategory);

        return buildings.stream().map(BuildingResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public BuildingDetailResponse getMapDetail(Long buildingId) {

        log.info("[건물 상세 조회]");

        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> {
                    log.warn("[건물 조회 실패] 존재하지 않는 건물 mapId: {}", buildingId);
                    return new CustomException(ErrorCode.BUILDING_NOT_FOUND);
                });

        return BuildingDetailResponse.from(building);
    }

    @Transactional(readOnly = true)
    public List<BuildingResponse> searchMaps(String keyword) {

        log.info("[지도 검색]");

        List<Building> buildings = buildingRepository.findByBuildingNameContainingOrderByCreatedAtDesc(keyword);

        return buildings.stream().map(BuildingResponse::from).toList();
    }
}
