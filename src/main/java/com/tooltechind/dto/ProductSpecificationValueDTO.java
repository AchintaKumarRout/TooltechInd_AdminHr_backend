package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationValueDTO {
    private Long specDefinitionId;
    private String specKey;
    private String specLabel;
    private String specUnit;
    private String dataType;
    private String specValue;
}