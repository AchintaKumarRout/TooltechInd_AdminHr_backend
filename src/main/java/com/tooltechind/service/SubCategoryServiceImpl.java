package com.tooltechind.service;

import com.tooltechind.dto.SubCategoryDTO;
import com.tooltechind.entity.ProductCategory;
import com.tooltechind.entity.SubCategory;
import com.tooltechind.repository.ProductCategoryRepository;
import com.tooltechind.repository.SubCategoryRepository;
import com.tooltechind.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // ===== CREATE =====
    @Override
    public SubCategoryDTO createSubCategory(SubCategoryDTO dto) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(dto.getSubCategoryName());
        subCategory.setImageUrl(dto.getImageUrl());

        ProductCategory productCategory = productCategoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("ProductCategory not found"));

        subCategory.setProductCategory(productCategory);

        SubCategory saved = subCategoryRepository.save(subCategory);
        return mapToDTO(saved);
    }

    // ===== UPDATE =====
    @Override
    public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO dto) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        subCategory.setSubCategoryName(dto.getSubCategoryName());
        subCategory.setImageUrl(dto.getImageUrl());

        if (dto.getCategoryId() != null) {
            ProductCategory productCategory = productCategoryRepository
                    .findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("ProductCategory not found"));
            subCategory.setProductCategory(productCategory);
        }

        SubCategory updated = subCategoryRepository.save(subCategory);
        return mapToDTO(updated);
    }

    // ===== GET BY ID =====
    @Override
    public SubCategoryDTO getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        return mapToDTO(subCategory);
    }

    // ===== GET ALL =====
    @Override
    public List<SubCategoryDTO> getAllSubCategories() {
        return subCategoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ===== DELETE =====
    @Override
    public void deleteSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        subCategoryRepository.delete(subCategory);
    }

    // ===== HELPER METHOD: ENTITY → DTO =====
    private SubCategoryDTO mapToDTO(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getSubCategoryName(),
                subCategory.getImageUrl(),
                subCategory.getProductCategory().getId()
        );
    }
    
    @Override
    public List<SubCategoryDTO> getSubCategoriesByCategoryId(Long categoryId) {
        return subCategoryRepository.findByProductCategoryId(categoryId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
