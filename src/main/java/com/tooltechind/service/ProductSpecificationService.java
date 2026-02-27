package com.tooltechind.service;

import com.tooltechind.dto.*;
import com.tooltechind.entity.*;
import com.tooltechind.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSpecificationService {

    private final ProductRepository productRepository;
    private final SpecificationDefinitionRepository specDefinitionRepository;
    private final ProductSpecificationValueRepository specValueRepository;
    private final SubCategoryRepository subCategoryRepository;

    /**
     * Get product with all its specifications
     */
    public ProductWithSpecsDTO getProductWithSpecs(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        SubCategory subCategory = product.getSubCategory();
        
        // Get available spec definitions for this subcategory
        List<SpecificationDefinition> specDefs = specDefinitionRepository
                .findBySubCategoryIdOrderByDisplayOrder(subCategory.getId());

        // Get current spec values for this product
        List<ProductSpecificationValue> specValues = specValueRepository.findByProductId(productId);
        
        // Map spec values for easy lookup
        Map<Long, ProductSpecificationValue> specValueMap = specValues.stream()
                .collect(Collectors.toMap(ProductSpecificationValue::getSpecDefinitionId, v -> v));

        // Build specification DTOs with current values
        List<ProductSpecificationValueDTO> specifications = specDefs.stream()
                .map(def -> {
                    ProductSpecificationValue value = specValueMap.get(def.getId());
                    return ProductSpecificationValueDTO.builder()
                            // Remove .id() - not in your DTO
                            .specDefinitionId(def.getId())
                            .specValue(value != null ? value.getSpecValue() : "")
                            .specKey(def.getSpecKey())
                            .specLabel(def.getSpecLabel())
                            .specUnit(def.getSpecUnit())
                            .dataType(def.getDataType())
                            .build();
                })
                .collect(Collectors.toList());

        // Build available specs DTOs
        List<SpecificationDefinitionDTO> availableSpecs = specDefs.stream()
                .map(def -> SpecificationDefinitionDTO.builder()
                        .id(def.getId())
                        .subCategoryId(def.getSubCategoryId())
                        .specKey(def.getSpecKey())
                        .specLabel(def.getSpecLabel())
                        .specUnit(def.getSpecUnit())
                        .dataType(def.getDataType())
                        .isRequired(def.getIsRequired())
                        .displayOrder(def.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return ProductWithSpecsDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .basePrice(product.getBasePrice())  // Changed from .price()
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .categoryId(subCategory.getProductCategory().getId())  // Changed from .getCategory()
                .categoryName(subCategory.getProductCategory().getName())  // Changed from .getCategory()
                .specifications(specifications)
                .availableSpecs(availableSpecs)
                .build();
    }

    /**
     * Update product specifications
     */
    @Transactional
    public ProductWithSpecsDTO updateProductSpecs(Long productId, UpdateProductSpecsRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update or create specification values
        request.getSpecifications().forEach((specDefId, value) -> {
            Optional<ProductSpecificationValue> existingOpt = specValueRepository
                    .findByProductIdAndSpecDefinitionId(productId, specDefId);

            if (existingOpt.isPresent()) {
                // Update existing
                ProductSpecificationValue existing = existingOpt.get();
                existing.setSpecValue(value);
                specValueRepository.save(existing);
            } else {
                // Create new
                ProductSpecificationValue newSpec = ProductSpecificationValue.builder()
                        .productId(productId)
                        .specDefinitionId(specDefId)
                        .specValue(value)
                        .build();
                specValueRepository.save(newSpec);
            }
        });

        return getProductWithSpecs(productId);
    }

    /**
     * Get specification definitions for a subcategory
     */
    public List<SpecificationDefinitionDTO> getSpecDefinitionsBySubCategory(Long subCategoryId) {
        return specDefinitionRepository.findBySubCategoryIdOrderByDisplayOrder(subCategoryId)
                .stream()
                .map(def -> SpecificationDefinitionDTO.builder()
                        .id(def.getId())
                        .subCategoryId(def.getSubCategoryId())
                        .specKey(def.getSpecKey())
                        .specLabel(def.getSpecLabel())
                        .specUnit(def.getSpecUnit())
                        .dataType(def.getDataType())
                        .isRequired(def.getIsRequired())
                        .displayOrder(def.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Delete all specifications for a product (when product is deleted)
     */
    @Transactional
    public void deleteProductSpecs(Long productId) {
        specValueRepository.deleteByProductId(productId);
    }
}