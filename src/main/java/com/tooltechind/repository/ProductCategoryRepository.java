package com.tooltechind.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tooltechind.dto.ProductCategoryDTO;
import com.tooltechind.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findBySlug(String slug);
}