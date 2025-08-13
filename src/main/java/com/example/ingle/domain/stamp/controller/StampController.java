package com.example.ingle.domain.stamp.controller;

import com.example.ingle.domain.stamp.StampService;
import com.example.ingle.domain.stamp.res.StampResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewards")
@Validated
public class StampController implements StampApiSpecification {

    private final StampService stampService;

    // 스탬프 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<StampResponse> getStamp(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(stampService.getStamp(id));
    }

    // 전체 스탬프 목록 조회
    @GetMapping
    public ResponseEntity<List<StampResponse>> getAllStamps() {
        return ResponseEntity.status(HttpStatus.OK).body(stampService.getAllStamps());
    }
}