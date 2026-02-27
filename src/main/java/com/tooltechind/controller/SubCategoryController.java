package com.tooltechind.controller;

import com.tooltechind.dto.SubCategoryDTO;
import com.tooltechind.service.FileStorageService;
import com.tooltechind.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private FileStorageService fileStorageService;

    // ===== CREATE SUB CATEGORY =====
    @PostMapping("/admin/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> createSubCategory(
            @RequestBody SubCategoryDTO dto) {
        return ResponseEntity.ok(subCategoryService.createSubCategory(dto));
    }

    // ===== UPDATE SUB CATEGORY =====
    @PutMapping("/admin/sub-categories/{subCategoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(
            @PathVariable Long subCategoryId,
            @RequestBody SubCategoryDTO dto) {
        return ResponseEntity.ok(
                subCategoryService.updateSubCategory(subCategoryId, dto)
        );
    }

    // ===== GET SUB CATEGORY BY ID =====
    @GetMapping("/sub-categories/{subCategoryId}")
    public ResponseEntity<SubCategoryDTO> getSubCategoryById(
            @PathVariable Long subCategoryId) {
        return ResponseEntity.ok(
                subCategoryService.getSubCategoryById(subCategoryId)
        );
    }
    // ===== GET ALL SUB CATEGORIES(ADMIN) =====
    @GetMapping("/admin/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategoriesByAdmin() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }
    // ===== GET ALL SUB CATEGORIES =====
    @GetMapping("/sub-categories")
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategories());
    }

    // ===== GET SUB CATEGORIES BY CATEGORY ID (IMPORTANT) =====
    @GetMapping("/admin/categories/{categoryId}/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubCategoryDTO>> getSubCategoriesByCategoryId(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(
                subCategoryService.getSubCategoriesByCategoryId(categoryId)
        );
    }

    // ===== DELETE SUB CATEGORY =====
    @DeleteMapping("/admin/sub-categories/{subCategoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubCategory(
            @PathVariable Long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.noContent().build();
    }

    // ===== UPLOAD SUB CATEGORY IMAGE (OPTIONAL) =====
//    @PostMapping("/admin/sub-categories/upload-image")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Map<String, String>> uploadSubCategoryImage(
//            @RequestParam("file") MultipartFile file) {
//
//        String imageUrl = fileStorageService.uploadSubCategoryImage(file);
//        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
//    }
}
