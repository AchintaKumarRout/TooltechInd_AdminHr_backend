package com.tooltechind.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ProductResponseDto {

    private Long productId;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private Boolean active;   
    private Long subCategoryId;

    // specKey -> specValue
    private Map<String, String> specifications;
}
