package com.tooltechind.service;

import com.tooltechind.dto.SubCategoryDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface SubCategoryService {
    SubCategoryDTO createSubCategory(String subCategoryName, Long categoryId, MultipartFile image);
    SubCategoryDTO updateSubCategory(Long id, String subCategoryName, Long categoryId, MultipartFile image);
    SubCategoryDTO getSubCategoryById(Long id);
    List<SubCategoryDTO> getAllSubCategories();
    void deleteSubCategory(Long id);
}