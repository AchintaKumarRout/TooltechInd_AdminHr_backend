package com.tooltechind.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductCreateRequest {

    private String productCode;
    private String productName;
    private BigDecimal price;
    private Long subCategoryId;

    // key = specificationDefinitionId
    private Map<Long, String> specifications;
}
