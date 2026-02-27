package com.tooltechind.service;

import com.tooltechind.dto.SubCategoryDTO;

import java.util.List;

public interface SubCategoryService {

    SubCategoryDTO createSubCategory(SubCategoryDTO dto);

    SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO dto);

    SubCategoryDTO getSubCategoryById(Long id);

    List<SubCategoryDTO> getAllSubCategories();

    void deleteSubCategory(Long id);
    List<SubCategoryDTO> getSubCategoriesByCategoryId(Long categoryId);
}
