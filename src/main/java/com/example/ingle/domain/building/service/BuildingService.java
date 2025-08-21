package com.example.ingle.domain.building.service;

import com.example.ingle.domain.building.dto.res.BuildingDetailResponse;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
import com.example.ingle.domain.building.entity.Building;
import com.example.ingle.domain.building.entity.ClosedDay;
import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.repository.BuildingRepository;
import com.example.ingle.domain.building.repository.ClosedDayRepository;
import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.domain.image.service.ImageService;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final ClosedDayRepository closedDayRepository;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<BuildingResponse> findMapsInBounds(double minLat, double maxLat, double minLng, double maxLng, BuildingCategory buildingCategory) {

        log.info("[지도 범위 조회]");

        List<Building> buildings = buildingRepository.findMapsInBounds(minLat, maxLat, minLng, maxLng, buildingCategory);

        return buildings.stream().map(BuildingResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public BuildingDetailResponse getMapDetail(Long buildingId) {

        log.info("[건물 상세 조회]");

        Building building = getBuildingById(buildingId);

        List<ClosedDay> closedDay = closedDayRepository.findByBuildingId(buildingId);

        return BuildingDetailResponse.from(building, closedDay);
    }

    @Transactional(readOnly = true)
    public List<BuildingResponse> searchMaps(String keyword) {

        log.info("[지도 검색]");

        List<Building> buildings = buildingRepository.findByBuildingNameContainingOrderByCreatedAtDesc(keyword);

        return buildings.stream().map(BuildingResponse::from).toList();
    }

    @Transactional
    public List<ImageResponse> saveBuildingImages(Long buildingId, List<MultipartFile> images) {

        log.info("[건물 이미지 등록] 건물 buildingId: {}", buildingId);

        Building building = getBuildingById(buildingId);

        deleteBuildingImages(building);

        List<ImageResponse> imageResponses = createImageResponses(building, images);

        buildingRepository.save(building);

        log.info("[건물 이미지 등록 성공] 건물 buildingId: {}, 이미지 개수: {}", buildingId, imageResponses.size());

        return imageResponses;
    }

    private Building getBuildingById(Long buildingId) {

        return buildingRepository.findById(buildingId)
                .orElseThrow(() -> {
                    log.warn("[건물 조회 실패] 존재하지 않는 건물 mapId: {}", buildingId);
                    return new CustomException(ErrorCode.BUILDING_NOT_FOUND);
                });
    }

    private List<ImageResponse> createImageResponses(Building building, List<MultipartFile> images) {

        return images.stream()
                .map(image -> {
                    ImageResponse response = imageService.saveImage(image);
                    building.getBuildingImages().add(response.fileName());
                    return response;
                })
                .toList();
    }

    private void deleteBuildingImages(Building building) {

        log.info("[건물 이미지 삭제] 건물 buildingId: {}", building.getId());

        if (!building.getBuildingImages().isEmpty()) {
            building.getBuildingImages().forEach(imageService::deleteImage);
            building.getBuildingImages().clear();
        }

        log.info("[건물 이미지 삭제 성공]");
    }
}
