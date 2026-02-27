package com.tooltechind.controller;

import com.tooltechind.dto.SubCategoryDTO;
import com.tooltechind.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    // ===== CREATE =====
    @PostMapping("/admin/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> createSubCategory(
            @RequestParam("subCategoryName") String subCategoryName,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        SubCategoryDTO result = subCategoryService.createSubCategory(subCategoryName, categoryId, image);
        return ResponseEntity.ok(result);
    }

    // ===== UPDATE =====
    @PutMapping("/admin/sub-categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(
            @PathVariable Long id,
            @RequestParam("subCategoryName") String subCategoryName,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        SubCategoryDTO result = subCategoryService.updateSubCategory(id, subCategoryName, categoryId, image);
        return ResponseEntity.ok(result);
    }

    // ===== GET BY ID =====
    @GetMapping("/sub-categories/{id}")
    public ResponseEntity<SubCategoryDTO> getSubCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(subCategoryService.getSubCategoryById(id));
    }

    // ===== GET ALL =====
    @GetMapping("/admin/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }

    // ===== DELETE =====
    @DeleteMapping("/admin/sub-categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}