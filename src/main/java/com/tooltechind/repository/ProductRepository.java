package com.tooltechind.repository;

import com.tooltechind.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Find by product code
    Optional<Product> findByProductCode(String productCode);

    // Find by sub-category
    List<Product> findBySubCategoryId(Long subCategoryId);

    // Find by sub-category and active status
    List<Product> findBySubCategoryIdAndIsActive(Long subCategoryId, Boolean isActive);

    // Find by category (through sub-category)
    @Query("SELECT p FROM Product p WHERE p.subCategory.productCategory.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Find by category and active status
    @Query("SELECT p FROM Product p WHERE p.subCategory.productCategory.id = :categoryId AND p.isActive = :isActive")
    List<Product> findByCategoryIdAndIsActive(@Param("categoryId") Long categoryId, @Param("isActive") Boolean isActive);

    // Find by shank taper
    List<Product> findByShankTaper(String shankTaper);

    // Find featured products
    List<Product> findByIsFeaturedTrue();

    // Find new products
    List<Product> findByIsNewProductTrue();

    // Search by product code or name
    @Query("SELECT p FROM Product p WHERE LOWER(p.productCode) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Product> searchByCodeOrName(@Param("search") String search);

    // Check if product code exists
    boolean existsByProductCode(String productCode);

    // Count products by sub-category
    long countBySubCategoryId(Long subCategoryId);

    // Count active products
    long countByIsActiveTrue();
}