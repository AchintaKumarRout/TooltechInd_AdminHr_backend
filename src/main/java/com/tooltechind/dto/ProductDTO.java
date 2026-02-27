package com.tooltechind.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String productCode;
    private String productName;
    private String model;
    private String shankTaper;
    private String size;
    
    // Specifications as a map for flexible display
    private Map<String, Object> specifications;
    
    private BigDecimal basePrice;
    private BigDecimal discountPrice;
    private String currency;
    
    private Integer stockQuantity;
    private Integer minOrderQuantity;
    private Integer leadTimeDays;
    
    private String imageUrl;
    private String technicalDrawingUrl;
    private Integer catalogPage;
    
    private Boolean isActive;
    private Boolean isFeatured;
    private Boolean isNewProduct;
    
    private String description;
    private String metaKeywords;
    private Integer displayOrder;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Sub-category info
    private Long subCategoryId;
    private String subCategoryName;
    
    // Category info
    private Long categoryId;
    private String categoryName;
}