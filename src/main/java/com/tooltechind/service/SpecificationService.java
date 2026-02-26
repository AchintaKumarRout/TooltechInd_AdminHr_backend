package com.tooltechind.service;

import com.tooltechind.dto.SpecificationDefinitionDto;

import java.util.List;

public interface SpecificationService {

    List<SpecificationDefinitionDto> getSpecificationsBySubCategory(Long subCategoryId);
    
    SpecificationDefinitionDto createSpecification(SpecificationDefinitionDto dto);

    SpecificationDefinitionDto updateSpecification(Long id, SpecificationDefinitionDto dto);

    void deleteSpecification(Long id);
}
