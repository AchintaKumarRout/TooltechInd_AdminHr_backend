package com.tooltechind.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "Product code is required")
    private String productCode;

    private String productName;

    private String model;

    private String shankTaper;

    private String size;

    @NotNull(message = "Sub-category ID is required")
    @Positive(message = "Sub-category ID must be positive")
    private Long subCategoryId;

    // Specifications as key-value pairs
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
}