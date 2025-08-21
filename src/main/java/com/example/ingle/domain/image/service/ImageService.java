package com.example.ingle.domain.image.service;

import com.example.ingle.domain.image.dto.response.ImageResponse;
import com.example.ingle.global.exception.CustomException;
import com.example.ingle.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload-dir}")
    private String fileDirectory;

    public ImageResponse saveImages(MultipartFile file) {

        File dir = new File(fileDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = convertJpeg(file);
        String url = "/api/v1/images?fileName=" + filePath;

        return ImageResponse.from(filePath, url);
    }

    public Resource getImages(String fileName) {

        try {
            Path path = Paths.get(fileDirectory).resolve(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.error("파일을 찾을 수 없습니다: {}", fileName);
                throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
            }

            return resource;
        } catch (MalformedURLException e) {
            log.error("잘못된 URL: {}", e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
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
}
