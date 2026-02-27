package com.tooltechind.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DTO designed for table display in UI
 * Combines core fields with flattened specifications for easy table rendering
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTableDTO {

    private Long id;
    private String productCode;
    private String model;
    private String shankTaper;
    
    // All specifications flattened for table display
    // Key = column name (e.g., "Ød", "ØD", "L1", "MAX RPM")
    // Value = actual value
    private Map<String, String> specifications;
    
    private BigDecimal basePrice;
    private Integer stockQuantity;
    private Boolean isActive;
    
    // For filtering
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;

    // Helper method to add specification
    public void addSpecification(String key, Object value) {
        if (this.specifications == null) {
            this.specifications = new LinkedHashMap<>();
        }
        this.specifications.put(key, value != null ? value.toString() : "");
    }
}
