package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithSpecsDTO {
    private Long id;
    private String productCode;
    private String productName;
    private BigDecimal basePrice;  // Changed from 'price' to match your Product entity
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;
    
    // Specifications with metadata for UI form generation
    private List<ProductSpecificationValueDTO> specifications;
    
    // Available specification definitions for the subcategory (optional)
    private List<SpecificationDefinitionDTO> availableSpecs;
}