package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationDefinitionDto {

    private Long id;
    private Long subCategoryId;
    private String specKey;
    private Integer displayOrder;
}
