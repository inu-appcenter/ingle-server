package com.example.ingle.domain.building.controller;

import com.example.ingle.domain.building.enums.BuildingCategory;
import com.example.ingle.domain.building.service.BuildingService;
import com.example.ingle.domain.building.dto.res.BuildingDetailResponse;
import com.example.ingle.domain.building.dto.res.BuildingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buildings")
public class BuildingController implements BuildingApiSpecification {

    private final BuildingService buildingService;

    // 지도 범위 조회 (카테고리)
    @GetMapping
    public ResponseEntity<List<BuildingResponse>> findMapsInBounds(
            @RequestParam double minLat, @RequestParam double minLng,
            @RequestParam double maxLat, @RequestParam double maxLng,
            @RequestParam(required = false) BuildingCategory buildingCategory) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.findMapsInBounds(minLat, maxLat, minLng, maxLng, buildingCategory));
    }

    // 건물 상세 조회
    @GetMapping("/{buildingId}")
    public ResponseEntity<BuildingDetailResponse> getMapDetail(@PathVariable Long buildingId) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.getMapDetail(buildingId));
    }

    // 지도 검색
    @GetMapping("/search")
    public ResponseEntity<List<BuildingResponse>> searchMaps(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.searchMaps(keyword));
    }
}
