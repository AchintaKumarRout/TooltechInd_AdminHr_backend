package com.tooltechind.controller;

import com.tooltechind.dto.ProductWithSpecsDTO;
import com.tooltechind.dto.SpecificationDefinitionDTO;
import com.tooltechind.dto.UpdateProductSpecsRequest;
import com.tooltechind.service.ProductSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
public class ProductSpecificationController {

    private final ProductSpecificationService specificationService;

    /**
     * Get product with all specifications
     */
    @GetMapping("/admin/products/{productId}/specifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductWithSpecsDTO> getProductWithSpecs(@PathVariable Long productId) {
        return ResponseEntity.ok(specificationService.getProductWithSpecs(productId));
    }

    /**
     * Update product specifications
     */
    @PutMapping("/admin/products/{productId}/specifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductWithSpecsDTO> updateProductSpecs(
            @PathVariable Long productId,
            @RequestBody UpdateProductSpecsRequest request) {
        return ResponseEntity.ok(specificationService.updateProductSpecs(productId, request));
    }

    /**
     * Get specification definitions for a subcategory
     */
    @GetMapping("/specification-definitions/sub-category/{subCategoryId}")
    public ResponseEntity<List<SpecificationDefinitionDTO>> getSpecDefinitions(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(specificationService.getSpecDefinitionsBySubCategory(subCategoryId));
    }
}
