package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationDefinitionDTO {
    private Long id;
    private Long subCategoryId;
    private String specKey;
    private String specLabel;
    private String specUnit;
    private String dataType; // decimal, number, text
    private Boolean isRequired;
    private Integer displayOrder;
}
