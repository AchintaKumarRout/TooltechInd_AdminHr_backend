package com.tooltechind.service;

import com.tooltechind.entity.SubCategory;
import com.tooltechind.entity.SubCategoryTechnicalDetails;
import com.tooltechind.repository.SubCategoryRepository;
import com.tooltechind.repository.SubCategoryTechnicalDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubCategoryTechnicalDetailsServiceImpl
        implements SubCategoryTechnicalDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SubCategoryTechnicalDetailsServiceImpl.class);

    private final SubCategoryTechnicalDetailsRepository repository;
    private final SubCategoryRepository subCategoryRepository;  // ADD THIS
    private final FileStorageService fileStorageService;

    @Override
    public SubCategoryTechnicalDetails saveOrUpdate(
            Long subCategoryId,
            MultipartFile image,
            MultipartFile drawing,
            String description) {

        // ── 1. Find or create technical details record ──
        SubCategoryTechnicalDetails details =
                repository.findBySubCategoryId(subCategoryId)
                        .orElse(new SubCategoryTechnicalDetails());

        details.setSubCategoryId(subCategoryId);

        // ── 2. Upload image → save URL in BOTH tables ──
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.uploadCategoryImage(image);
            log.info("Image uploaded to S3: {}", imageUrl);

            // Save in sub_category_technical_details
            details.setImageUrl(imageUrl);

            // Save in sub_categories table also
            SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + subCategoryId));
            subCategory.setImageUrl(imageUrl);
            subCategoryRepository.save(subCategory);
            log.info("Image URL saved in sub_categories table for id: {}", subCategoryId);
        }

        // ── 3. Upload technical drawing → only in technical details table ──
        if (drawing != null && !drawing.isEmpty()) {
            String drawingUrl = fileStorageService.uploadCategoryImage(drawing);
            log.info("Drawing uploaded to S3: {}", drawingUrl);
            details.setTechnicalDrawingUrl(drawingUrl);
        }

        details.setDescription(description);
        details.setUpdatedAt(LocalDateTime.now());

        if (details.getCreatedAt() == null) {
            details.setCreatedAt(LocalDateTime.now());
        }

        SubCategoryTechnicalDetails saved = repository.save(details);
        log.info("Saved technical details - imageUrl: {}", saved.getImageUrl());

        return saved;
    }

    @Override
    public SubCategoryTechnicalDetails getBySubCategoryId(Long subCategoryId) {
        return repository.findBySubCategoryId(subCategoryId)
                .orElse(null);
    }
}