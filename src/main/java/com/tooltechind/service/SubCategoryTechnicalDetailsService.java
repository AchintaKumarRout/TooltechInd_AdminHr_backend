package com.tooltechind.service;



import com.tooltechind.entity.SubCategoryTechnicalDetails;
import org.springframework.web.multipart.MultipartFile;

public interface SubCategoryTechnicalDetailsService {

    SubCategoryTechnicalDetails saveOrUpdate(
            Long subCategoryId,
            MultipartFile image,
            MultipartFile drawing,
            String description
    );

    SubCategoryTechnicalDetails getBySubCategoryId(Long subCategoryId);

}

