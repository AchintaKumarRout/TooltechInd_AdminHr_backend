package com.tooltechind.service;

import com.tooltechind.dto.*;
import com.tooltechind.entity.Product;
import com.tooltechind.entity.SpecificationDefinition;
import com.tooltechind.entity.SubCategory;
import com.tooltechind.repository.ProductRepository;
import com.tooltechind.repository.SpecificationDefinitionRepository;
import com.tooltechind.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SpecificationDefinitionRepository specDefinitionRepository;

    /**
     * Get product with specifications formatted for editing
     */
    public ProductWithSpecsDTO getProductWithSpecs(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        SubCategory subCategory = product.getSubCategory();
        
        // Get specification definitions for this subcategory
        List<SpecificationDefinition> specDefs = specDefinitionRepository
                .findBySubCategoryIdOrderByDisplayOrder(subCategory.getId());

        // Get current specification values from JSONB
        Map<String, Object> currentSpecs = product.getSpecifications() != null 
            ? product.getSpecifications() 
            : new HashMap<>();

        // Build specification DTOs with current values
        List<ProductSpecificationValueDTO> specifications = specDefs.stream()
                .map(def -> {
                    Object value = currentSpecs.get(def.getSpecKey());
                    return ProductSpecificationValueDTO.builder()
                            .specDefinitionId(def.getId())
                            .specKey(def.getSpecKey())
                            .specLabel(def.getSpecLabel())
                            .specUnit(def.getSpecUnit())
                            .dataType(def.getDataType())
                            .specValue(value != null ? value.toString() : "")
                            .build();
                })
                .collect(Collectors.toList());

        return ProductWithSpecsDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .basePrice(product.getBasePrice())
                .subCategoryId(subCategory.getId())
                .subCategoryName(subCategory.getSubCategoryName())
                .categoryId(subCategory.getProductCategory().getId())
                .categoryName(subCategory.getProductCategory().getName())
                .specifications(specifications)
                .build();
    }

    /**
     * Update product specifications (JSONB approach)
     */
    @Transactional
    public ProductDTO updateProductSpecs(Long productId, Map<String, Object> specifications) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update the JSONB specifications field
        product.setSpecifications(specifications);
        
        Product updated = productRepository.save(product);
        return convertToDTO(updated);
    }

    /**
     * Create a new product
     */
    @Transactional
    public ProductDTO createProduct(ProductCreateRequest request) {
        // Check if product code already exists
        if (productRepository.existsByProductCode(request.getProductCode())) {
            throw new RuntimeException("Product code already exists: " + request.getProductCode());
        }

        // Fetch sub-category
        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("Sub-category not found: " + request.getSubCategoryId()));

        // Create product
        Product product = new Product();
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setModel(request.getModel());
        product.setShankTaper(request.getShankTaper());
        product.setSize(request.getSize());
        product.setSubCategory(subCategory);
        product.setSpecifications(request.getSpecifications());
        product.setBasePrice(request.getBasePrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setCurrency(request.getCurrency() != null ? request.getCurrency() : "INR");
        product.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);
        product.setMinOrderQuantity(request.getMinOrderQuantity() != null ? request.getMinOrderQuantity() : 1);
        product.setLeadTimeDays(request.getLeadTimeDays());
        product.setImageUrl(request.getImageUrl());
        product.setTechnicalDrawingUrl(request.getTechnicalDrawingUrl());
        product.setCatalogPage(request.getCatalogPage());
        product.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        product.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        product.setIsNewProduct(request.getIsNewProduct() != null ? request.getIsNewProduct() : false);
        product.setDescription(request.getDescription());
        product.setMetaKeywords(request.getMetaKeywords());
        product.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    /**
     * Update existing product
     */
    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        // Check if product code changed and if new code already exists
        if (!product.getProductCode().equals(request.getProductCode())) {
            if (productRepository.existsByProductCode(request.getProductCode())) {
                throw new RuntimeException("Product code already exists: " + request.getProductCode());
            }
        }

        // Update sub-category if changed
        if (!product.getSubCategory().getId().equals(request.getSubCategoryId())) {
            SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("Sub-category not found: " + request.getSubCategoryId()));
            product.setSubCategory(subCategory);
        }

        // Update fields
        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setModel(request.getModel());
        product.setShankTaper(request.getShankTaper());
        product.setSize(request.getSize());
        product.setSpecifications(request.getSpecifications());
        product.setBasePrice(request.getBasePrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setCurrency(request.getCurrency());
        product.setStockQuantity(request.getStockQuantity());
        product.setMinOrderQuantity(request.getMinOrderQuantity());
        product.setLeadTimeDays(request.getLeadTimeDays());
        product.setImageUrl(request.getImageUrl());
        product.setTechnicalDrawingUrl(request.getTechnicalDrawingUrl());
        product.setCatalogPage(request.getCatalogPage());
        product.setIsActive(request.getIsActive());
        product.setIsFeatured(request.getIsFeatured());
        product.setIsNewProduct(request.getIsNewProduct());
        product.setDescription(request.getDescription());
        product.setMetaKeywords(request.getMetaKeywords());
        product.setDisplayOrder(request.getDisplayOrder());

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    /**
     * Get product by ID
     */
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        return convertToDTO(product);
    }

    /**
     * Get product by code
     */
    public ProductDTO getProductByCode(String code) {
        Product product = productRepository.findByProductCode(code)
                .orElseThrow(() -> new RuntimeException("Product not found: " + code));
        return convertToDTO(product);
    }

    /**
     * Get all products
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get products by sub-category
     */
    public List<ProductDTO> getProductsBySubCategory(Long subCategoryId) {
        return productRepository.findBySubCategoryId(subCategoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get products by category
     */
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get products in table format for UI
     */
    public List<ProductTableDTO> getProductsTableBySubCategory(Long subCategoryId) {
        List<Product> products = productRepository.findBySubCategoryId(subCategoryId);
        return products.stream()
                .map(this::convertToTableDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search products
     */
    public List<ProductDTO> searchProducts(String search) {
        return productRepository.searchByCodeOrName(search).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Delete product
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Get paginated products
     */
    public Page<ProductDTO> getProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // ========================================
    // Helper Methods
    // ========================================

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .model(product.getModel())
                .shankTaper(product.getShankTaper())
                .size(product.getSize())
                .specifications(product.getSpecifications())
                .basePrice(product.getBasePrice())
                .discountPrice(product.getDiscountPrice())
                .currency(product.getCurrency())
                .stockQuantity(product.getStockQuantity())
                .minOrderQuantity(product.getMinOrderQuantity())
                .leadTimeDays(product.getLeadTimeDays())
                .imageUrl(product.getImageUrl())
                .technicalDrawingUrl(product.getTechnicalDrawingUrl())
                .catalogPage(product.getCatalogPage())
                .isActive(product.getIsActive())
                .isFeatured(product.getIsFeatured())
                .isNewProduct(product.getIsNewProduct())
                .description(product.getDescription())
                .metaKeywords(product.getMetaKeywords())
                .displayOrder(product.getDisplayOrder())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .subCategoryId(product.getSubCategory().getId())
                .subCategoryName(product.getSubCategory().getSubCategoryName())
                .categoryId(product.getSubCategory().getProductCategory().getId())
                .categoryName(product.getSubCategory().getProductCategory().getName())
                .build();
    }

    private ProductTableDTO convertToTableDTO(Product product) {
        ProductTableDTO dto = ProductTableDTO.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .model(product.getModel())
                .shankTaper(product.getShankTaper())
                .basePrice(product.getBasePrice())
                .stockQuantity(product.getStockQuantity())
                .isActive(product.getIsActive())
                .subCategoryId(product.getSubCategory().getId())
                .subCategoryName(product.getSubCategory().getSubCategoryName())
                .categoryId(product.getSubCategory().getProductCategory().getId())
                .categoryName(product.getSubCategory().getProductCategory().getName())
                .specifications(new LinkedHashMap<>())
                .build();

        // Flatten specifications for table display
        if (product.getSpecifications() != null) {
            product.getSpecifications().forEach((key, value) -> {
                dto.addSpecification(key, value);
            });
        }

        return dto;
    }
}