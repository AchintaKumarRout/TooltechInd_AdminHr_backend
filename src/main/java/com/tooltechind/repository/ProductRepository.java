package com.tooltechind.repository;

import com.tooltechind.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySubCategoryId(Long subCategoryId);
}
