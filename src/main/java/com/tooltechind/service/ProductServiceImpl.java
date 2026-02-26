package com.tooltechind.service;

import com.tooltechind.dto.*;
import com.tooltechind.entity.*;
import com.tooltechind.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SpecificationDefinitionRepository specDefRepo;
    private final ProductSpecificationValueRepository specValueRepo;

    @Override
    public void createProduct(ProductCreateRequest request) {

        SubCategory subCategory = subCategoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        Product product = productRepository.save(
                Product.builder()
                        .productCode(request.getProductCode())
                        .productName(request.getProductName())
                        .price(request.getPrice())
                        .subCategory(subCategory)
                        .active(true)
                        .build()
        );

        request.getSpecifications().forEach((specDefId, value) -> {
            SpecificationDefinition def = specDefRepo.findById(specDefId)
                    .orElseThrow(() -> new RuntimeException("Specification not found"));

            specValueRepo.save(
                    ProductSpecificationValue.builder()
                            .product(product)
                            .specificationDefinition(def)
                            .specValue(value)
                            .build()
            );
        });
    }

    @Override
    public void updateProduct(Long productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());

        productRepository.save(product);

        // 🔴 IMPORTANT: replace existing specs
        specValueRepo.deleteByProductId(productId);

        request.getSpecifications().forEach((specDefId, value) -> {
            SpecificationDefinition def = specDefRepo.findById(specDefId)
                    .orElseThrow(() -> new RuntimeException("Specification not found"));

            specValueRepo.save(
                    ProductSpecificationValue.builder()
                            .product(product)
                            .specificationDefinition(def)
                            .specValue(value)
                            .build()
            );
        });
    }

    @Override
    public ProductResponseDto getProductDetails(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductSpecificationValue> values =
                specValueRepo.findByProductId(productId);

        Map<String, String> specMap = new LinkedHashMap<>();
        values.forEach(v ->
                specMap.put(
                        v.getSpecificationDefinition().getSpecKey(),
                        v.getSpecValue()
                )
        );

        return ProductResponseDto.builder()
                .productId(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .price(product.getPrice())
                .active(product.getActive())
                .subCategoryId(product.getSubCategory().getId())
                .specifications(specMap)
                .build();
    }

    @Override
    public List<ProductResponseDto> getProductsBySubCategory(Long subCategoryId) {
        return productRepository.findBySubCategoryId(subCategoryId)
                .stream()
                .map(p -> getProductDetails(p.getId()))
                .toList();
    }
}
