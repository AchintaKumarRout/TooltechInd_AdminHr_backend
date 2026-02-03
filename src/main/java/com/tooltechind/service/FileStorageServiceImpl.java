package com.tooltechind.service;



import com.tooltechind.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    @Override
    public String uploadCategoryImage(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = "category-" + UUID.randomUUID() + "." + ext;

            Path dirPath = Paths.get(uploadDir, "categories");
            Files.createDirectories(dirPath);

            Path filePath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return baseUrl + "/categories/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
