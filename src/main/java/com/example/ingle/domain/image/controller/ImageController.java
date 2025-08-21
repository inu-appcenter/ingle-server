package com.example.ingle.domain.image.controller;

import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponse> saveImages(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.saveImages(file));
    }

    @GetMapping
    public ResponseEntity<Resource> getImages(@RequestParam("fileName") String fileName) {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImages(fileName));
    }
}
