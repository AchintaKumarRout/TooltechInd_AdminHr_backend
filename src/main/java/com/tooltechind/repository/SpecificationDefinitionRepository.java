package com.tooltechind.repository;

import com.tooltechind.entity.SpecificationDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecificationDefinitionRepository extends JpaRepository<SpecificationDefinition, Long> {
    
    /**
     * Find all specification definitions for a subcategory, ordered by display_order
     */
    List<SpecificationDefinition> findBySubCategoryIdOrderByDisplayOrder(Long subCategoryId);
    
    /**
     * Check if a spec key exists for a subcategory
     */
    boolean existsBySubCategoryIdAndSpecKey(Long subCategoryId, String specKey);
    
    /**
     * Delete all specs for a subcategory
     */
    void deleteBySubCategoryId(Long subCategoryId);
}