package com.tooltechind.repository;

import com.tooltechind.entity.ProductSpecificationValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSpecificationValueRepository
        extends JpaRepository<ProductSpecificationValue, Long> {

    List<ProductSpecificationValue> findByProductId(Long productId);
    
    void deleteByProductId(Long productId);

}
