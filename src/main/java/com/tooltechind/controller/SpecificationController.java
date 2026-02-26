package com.tooltechind.controller;

import com.tooltechind.dto.SpecificationDefinitionDto;
import com.tooltechind.service.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specifications")
@RequiredArgsConstructor
public class SpecificationController {

    private final SpecificationService specificationService;

    // ================= READ (PUBLIC / ADMIN) =================

    @GetMapping("/by-subcategory/{subCategoryId}")
    public List<SpecificationDefinitionDto> getSpecs(
            @PathVariable Long subCategoryId) {

        return specificationService.getSpecificationsBySubCategory(subCategoryId);
    }

    // ================= CREATE (ADMIN) =================

    @PostMapping
    public SpecificationDefinitionDto createSpecification(
            @RequestBody SpecificationDefinitionDto dto) {

        return specificationService.createSpecification(dto);
    }

    // ================= UPDATE (ADMIN) =================

    @PutMapping("/{id}")
    public SpecificationDefinitionDto updateSpecification(
            @PathVariable Long id,
            @RequestBody SpecificationDefinitionDto dto) {

        return specificationService.updateSpecification(id, dto);
    }

    // ================= DELETE (ADMIN) =================

    @DeleteMapping("/{id}")
    public void deleteSpecification(@PathVariable Long id) {
        specificationService.deleteSpecification(id);
    }
}
