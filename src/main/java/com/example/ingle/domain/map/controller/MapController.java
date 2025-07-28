package com.example.ingle.domain.map.controller;

import com.example.ingle.domain.map.MapCategory;
import com.example.ingle.domain.map.MapService;
import com.example.ingle.domain.map.dto.res.MapDetailResponseDto;
import com.example.ingle.domain.map.dto.res.MapResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maps")
public class MapController implements MapApiSpecification {

    private final MapService mapService;

    // 지도 범위 조회 (카테고리)
    @GetMapping
    public ResponseEntity<List<MapResponseDto>> getMapsInBounds(
            @RequestParam double minLat, @RequestParam double minLng,
            @RequestParam double maxLat, @RequestParam double maxLng,
            @RequestParam(required = false) MapCategory category) {
        return ResponseEntity.status(HttpStatus.OK).body(mapService.findMapsInBounds(minLat, maxLat, minLng, maxLng, category));
    }

    // 건물 상세 조회
    @GetMapping("/{mapId}")
    public ResponseEntity<MapDetailResponseDto> getMapDetail(@PathVariable Long mapId) {
        return ResponseEntity.status(HttpStatus.OK).body(mapService.findById(mapId));
    }

    // 지도 검색
    @GetMapping("/search")
    public ResponseEntity<List<MapResponseDto>> searchMaps(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(mapService.searchMaps(keyword));
    }
}
