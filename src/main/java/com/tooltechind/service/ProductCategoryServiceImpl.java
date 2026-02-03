package com.tooltechind.service;

import com.tooltechind.dto.ProductCategoryDTO;
import com.tooltechind.entity.ProductCategory;
import com.tooltechind.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    // Entity → DTO
    private ProductCategoryDTO toDTO(ProductCategory entity) {
        return new ProductCategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getImgUrl(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // DTO → Entity
    private ProductCategory toEntity(ProductCategoryDTO dto) {
        ProductCategory entity = new ProductCategory();
        entity.setName(dto.getName());
        entity.setSlug(dto.getSlug());
        entity.setImgUrl(dto.getImgUrl());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);
        return entity;
    }

    @Override
    public ProductCategoryDTO createCategory(ProductCategoryDTO categoryDTO) {
        ProductCategory entity = toEntity(categoryDTO);
        ProductCategory saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    public ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO categoryDTO) {
        ProductCategory existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        existing.setName(categoryDTO.getName());
        existing.setSlug(categoryDTO.getSlug());
        existing.setImgUrl(categoryDTO.getImgUrl());
        existing.setActive(categoryDTO.getActive());

        ProductCategory updated = repository.save(existing);
        return toDTO(updated);
    }

    @Override
    public ProductCategoryDTO getCategoryById(Long id) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return toDTO(entity);
    }

    @Override
    public List<ProductCategoryDTO> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
