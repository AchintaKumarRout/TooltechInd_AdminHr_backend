package com.tooltechind.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")        // ADD THIS
    private String region;

    private final S3Client s3Client;

    public FileStorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadCategoryImage(MultipartFile file) {
        return uploadFile(file, "categories");
    }

    @Override
    public String uploadNewsImage(MultipartFile file) {
        return uploadFile(file, "news");
    }

    private String uploadFile(MultipartFile file, String folder) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        try {
            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String key = folder + "/" + UUID.randomUUID() + "." + ext;

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Correct URL format with region
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;

        } catch (Exception e) {
            throw new RuntimeException("S3 upload failed for folder: " + folder, e);
        }
    }
}