package com.example.ingle.domain.image.service;

import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

        String filePath = convertJpeg(file);
        String url = "/api/v1/images/" + filePath;

        return ImageResponse.from(filePath, url);
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

    public void deleteImage(String fileName) {
        try {
            Path path = getFilePath(fileName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("이미지 삭제 실패: {}", e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_DELETION_FAILED);
        }
    }

    private String convertJpeg(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            String fileName = UUID.randomUUID() + ".jpg";
            File outputFile = new File(fileDirectory, fileName);

            ImageIO.write(bufferedImage, "jpg", outputFile);

            return fileName;
        } catch (IOException e) {
            log.error("이미지 변환 실패: {}", e.getMessage());
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
