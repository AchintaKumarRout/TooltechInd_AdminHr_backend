package com.tooltechind.service;

import java.util.List;

import com.tooltechind.dto.ProductCategoryDTO;

public interface ProductCategoryService {

    ProductCategoryDTO createCategory(ProductCategoryDTO categoryDTO);

    ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO categoryDTO);

    ProductCategoryDTO getCategoryById(Long id);

    List<ProductCategoryDTO> getAllCategories();

    void deleteCategory(Long id);
   
}