package com.tooltechind.service;

import com.tooltechind.dto.ProductCreateRequest;
import com.tooltechind.dto.ProductResponseDto;
import com.tooltechind.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    void createProduct(ProductCreateRequest request);

    List<ProductResponseDto> getProductsBySubCategory(Long subCategoryId);

    ProductResponseDto getProductDetails(Long productId);
    
    void updateProduct(Long productId, ProductUpdateRequest request);
}
