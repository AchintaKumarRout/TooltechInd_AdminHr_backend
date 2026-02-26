package com.tooltechind.service;

import com.tooltechind.dto.SpecificationDefinitionDto;
import com.tooltechind.entity.SpecificationDefinition;
import com.tooltechind.entity.SubCategory;
import com.tooltechind.repository.SpecificationDefinitionRepository;
import com.tooltechind.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    private final SpecificationDefinitionRepository specRepo;
    private final SubCategoryRepository subCategoryRepo;

    @Override
    public List<SpecificationDefinitionDto> getSpecificationsBySubCategory(Long subCategoryId) {
        return specRepo
                .findBySubCategoryIdOrderByDisplayOrderAsc(subCategoryId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SpecificationDefinitionDto createSpecification(SpecificationDefinitionDto dto) {

        SubCategory subCategory = subCategoryRepo.findById(dto.getSubCategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        SpecificationDefinition spec = SpecificationDefinition.builder()
                .subCategory(subCategory)
                .specKey(dto.getSpecKey())
                .displayOrder(dto.getDisplayOrder())
                .build();

        return toDto(specRepo.save(spec));
    }

    @Override
    public SpecificationDefinitionDto updateSpecification(Long id, SpecificationDefinitionDto dto) {

        SpecificationDefinition spec = specRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Specification not found"));

        spec.setSpecKey(dto.getSpecKey());
        spec.setDisplayOrder(dto.getDisplayOrder());

        return toDto(specRepo.save(spec));
    }

    @Override
    public void deleteSpecification(Long id) {
        specRepo.deleteById(id);
    }

    private SpecificationDefinitionDto toDto(SpecificationDefinition spec) {
        return SpecificationDefinitionDto.builder()
                .id(spec.getId())
                .subCategoryId(spec.getSubCategory().getId())
                .specKey(spec.getSpecKey())
                .displayOrder(spec.getDisplayOrder())
                .build();
    }
}
