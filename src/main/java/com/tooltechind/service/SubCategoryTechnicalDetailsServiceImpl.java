package com.tooltechind.service;

import com.tooltechind.entity.SubCategoryTechnicalDetails;
import com.tooltechind.repository.SubCategoryTechnicalDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubCategoryTechnicalDetailsServiceImpl
        implements SubCategoryTechnicalDetailsService {

    private final SubCategoryTechnicalDetailsRepository repository;

    private final FileStorageService fileStorageService;


    @Override
    public SubCategoryTechnicalDetails saveOrUpdate(
            Long subCategoryId,
            MultipartFile image,
            MultipartFile drawing,
            String description) {

        SubCategoryTechnicalDetails details =
                repository.findBySubCategoryId(subCategoryId)
                        .orElse(new SubCategoryTechnicalDetails());

        details.setSubCategoryId(subCategoryId);


        if (image != null && !image.isEmpty()) {

            String imageUrl =
                    fileStorageService.uploadCategoryImage(image);

            details.setImageUrl(imageUrl);
        }


        if (drawing != null && !drawing.isEmpty()) {

            String drawingUrl =
                    fileStorageService.uploadCategoryImage(drawing);

            details.setTechnicalDrawingUrl(drawingUrl);
        }


        details.setDescription(description);

        details.setUpdatedAt(LocalDateTime.now());

        if (details.getCreatedAt() == null) {

            details.setCreatedAt(LocalDateTime.now());

        }

        return repository.save(details);
    }


    @Override
    public SubCategoryTechnicalDetails getBySubCategoryId(Long subCategoryId) {

        return repository.findBySubCategoryId(subCategoryId)
                .orElse(null);

    }

}
