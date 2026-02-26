package com.tooltechind.repository;


import com.tooltechind.entity.SubCategoryTechnicalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryTechnicalDetailsRepository
        extends JpaRepository<SubCategoryTechnicalDetails, Long> {

    Optional<SubCategoryTechnicalDetails> findBySubCategoryId(Long subCategoryId);

}
