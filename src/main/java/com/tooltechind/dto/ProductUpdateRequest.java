package com.tooltechind.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductUpdateRequest {

    private String productName;
    private BigDecimal price;
    private Boolean active;

    // key = specificationDefinitionId
    private Map<Long, String> specifications;
}
