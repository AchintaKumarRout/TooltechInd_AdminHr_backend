package com.tooltechind.controller;

import com.tooltechind.dto.ProductCreateRequest;
import com.tooltechind.dto.ProductResponseDto;
import com.tooltechind.dto.ProductUpdateRequest;
import com.tooltechind.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ADMIN - CREATE
    @PostMapping
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.createProduct(request);
    }

    // ADMIN - UPDATE (CRITICAL)
    @PutMapping("/{productId}")
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request) {

        productService.updateProduct(productId, request);
    }

    // ADMIN + USER
    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProductDetails(productId);
    }

    // USER
    @GetMapping("/by-subcategory/{subCategoryId}")
    public List<ProductResponseDto> getBySubCategory(
            @PathVariable Long subCategoryId) {
        return productService.getProductsBySubCategory(subCategoryId);
    }
}
