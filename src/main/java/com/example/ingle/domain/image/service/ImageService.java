package com.example.ingle.domain.image.service;

import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload-dir}")
    private String fileDirectory;

    public ImageResponse saveImage(MultipartFile file) {
        File dir = new File(fileDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = extractExtension(originalFilename);

        String fileName = UUID.randomUUID() + extension;
        File dest = new File(fileDirectory, fileName);

        transferTo(file, dest);

        String url = "/api/v1/images/" + fileName;

        return ImageResponse.from(fileName, url);
    }

    public byte[] getImage(String fileName) {
        try {
            Path path = getFilePath(fileName);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("이미지 읽기 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }

    public String getContentType(String filename) {
        Path path = Paths.get(fileDirectory).resolve(filename);
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            log.warn("파일 MIME 타입 확인 실패: {}", filename);
        }

        return contentType != null ? contentType : "application/octet-stream";
    }

    public void deleteImage(String fileName) {
        try {
            Path path = getFilePath(fileName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("이미지 삭제 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_DELETION_FAILED);
        }
    }

    private String extractExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        }
        return "";
    }

    private void transferTo(MultipartFile file, File dest) {
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("이미지 저장 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_CONVERSION_FAILED);
        }
    }

    private Path getFilePath(String fileName) {
        Path path = Paths.get(fileDirectory).resolve(fileName).normalize();
        if (!path.startsWith(Paths.get(fileDirectory))) {
            log.error("잘못된 파일 경로 접근 시도: {}", fileName);
            throw new CustomException(ErrorCode.INVALID_FILE_PATH);
        }
        return path;
    }
}
