package com.tooltechind.repository;

import com.tooltechind.entity.SubCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    // You can add custom queries if needed
	 List<SubCategory> findByProductCategoryId(Long categoryId);
}
