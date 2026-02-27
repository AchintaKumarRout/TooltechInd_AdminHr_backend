package com.tooltechind.repository;

import com.tooltechind.entity.ProductSpecificationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSpecificationValueRepository extends JpaRepository<ProductSpecificationValue, Long> {
    List<ProductSpecificationValue> findByProductId(Long productId);
    Optional<ProductSpecificationValue> findByProductIdAndSpecDefinitionId(Long productId, Long specDefinitionId);
    void deleteByProductId(Long productId);
}