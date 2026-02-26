package com.tooltechind.repository;

import com.tooltechind.entity.SpecificationDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificationDefinitionRepository
        extends JpaRepository<SpecificationDefinition, Long> {

    List<SpecificationDefinition> findBySubCategoryIdOrderByDisplayOrderAsc(Long subCategoryId);
}
