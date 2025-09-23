package com.example.ingle.domain.building.service;

import com.example.ingle.domain.building.dto.res.BuildingDetailResponse;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
import com.example.ingle.domain.building.domain.Building;
import com.example.ingle.domain.building.domain.ClosedDay;
import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.repository.BuildingRepository;
import com.example.ingle.domain.building.repository.ClosedDayRepository;
import com.example.ingle.domain.image.dto.res.ImageResponse;
import com.example.ingle.domain.image.service.ImageService;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        return buildingRepository.findBuildingsInBounds(minLat, maxLat, minLng, maxLng, buildingCategory);
    }

    @Transactional(readOnly = true)
    public BuildingDetailResponse getMapDetail(Long buildingId) {
        Building building = getBuildingById(buildingId);

        List<ClosedDay> closedDay = closedDayRepository.findByBuildingId(buildingId);

        return BuildingDetailResponse.from(building, closedDay);
    }

    @Transactional(readOnly = true)
    public List<BuildingResponse> searchMaps(String keyword) {
        return buildingRepository.findByBuildingNameContainingOrderByCreatedAtDesc(keyword);
    }

    @Transactional
    public List<ImageResponse> saveBuildingImages(Long buildingId, List<MultipartFile> images) {
        Building building = getBuildingById(buildingId);

        deleteBuildingImages(building);

        List<ImageResponse> imageResponses = new ArrayList<>();
        if (!images.isEmpty()) {
            imageResponses = createImageResponses(building, images);
        }

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
        if (!building.getBuildingImages().isEmpty()) {
            building.getBuildingImages().forEach(imageService::deleteImage);
            building.getBuildingImages().clear();
        }
    }
}
