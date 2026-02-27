package com.tooltechind.service;

import com.tooltechind.dto.SubCategoryDTO;
import com.tooltechind.entity.ProductCategory;
import com.tooltechind.entity.SubCategory;
import com.tooltechind.repository.ProductCategoryRepository;
import com.tooltechind.repository.SubCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    private static final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public SubCategoryDTO createSubCategory(String subCategoryName, Long categoryId, MultipartFile image) {
        log.info("Creating SubCategory: name={}, categoryId={}, hasImage={}", 
                 subCategoryName, categoryId, image != null && !image.isEmpty());

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(subCategoryName);

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.uploadCategoryImage(image);
            log.info("Image uploaded to S3: {}", imageUrl);
            subCategory.setImageUrl(imageUrl);
        }

        ProductCategory productCategory = productCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("ProductCategory not found with id: " + categoryId));

        subCategory.setProductCategory(productCategory);

        SubCategory saved = subCategoryRepository.save(subCategory);
        log.info("Saved to DB - id={}, imageUrl={}", saved.getId(), saved.getImageUrl());

        return mapToDTO(saved);
    }

    @Override
    public SubCategoryDTO updateSubCategory(Long id, String subCategoryName, Long categoryId, MultipartFile image) {
        log.info("Updating SubCategory id={}, hasImage={}", id, image != null && !image.isEmpty());

        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found with id: " + id));

        subCategory.setSubCategoryName(subCategoryName);

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.uploadCategoryImage(image);
            log.info("New image uploaded to S3: {}", imageUrl);
            subCategory.setImageUrl(imageUrl);
        }

        if (categoryId != null) {
            ProductCategory productCategory = productCategoryRepository
                    .findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("ProductCategory not found with id: " + categoryId));
            subCategory.setProductCategory(productCategory);
        }

        SubCategory updated = subCategoryRepository.save(subCategory);
        log.info("Updated in DB - id={}, imageUrl={}", updated.getId(), updated.getImageUrl());

        return mapToDTO(updated);
    }

    @Override
    public SubCategoryDTO getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        return mapToDTO(subCategory);
    }

    @Override
    public List<SubCategoryDTO> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        subCategoryRepository.delete(subCategory);
    }

    private SubCategoryDTO mapToDTO(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getSubCategoryName(),
                subCategory.getImageUrl(),
                subCategory.getProductCategory().getId()
        );
    }
}