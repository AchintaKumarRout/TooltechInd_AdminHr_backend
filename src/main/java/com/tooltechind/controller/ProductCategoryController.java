package com.tooltechind.controller;

import com.tooltechind.dto.ProductCategoryDTO;
import com.tooltechind.service.FileStorageService;
import com.tooltechind.service.ProductCategoryService;
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
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService service;

    @Autowired
    private FileStorageService fileStorageService;

    // ===== CREATE CATEGORY =====
    @PostMapping("/admin/product-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryDTO> createCategory(
            @RequestBody ProductCategoryDTO dto) {
        return ResponseEntity.ok(service.createCategory(dto));
    }

    // ===== UPDATE CATEGORY =====
    @PutMapping("/admin/product-categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryDTO> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody ProductCategoryDTO dto) {
        return ResponseEntity.ok(service.updateCategory(categoryId, dto));
    }

    // ===== GET CATEGORY BY ID (PUBLIC) =====
    @GetMapping("/product-categories/{categoryId}")
    public ResponseEntity<ProductCategoryDTO> getCategoryById(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getCategoryById(categoryId));
    }
    // ===== GET ALL CATEGORIES (ADMIN) =====
    @GetMapping("admin/product-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategoriesByAdmin() {
        return ResponseEntity.ok(service.getAllCategories());
    }
    // ===== GET ALL CATEGORIES (PUBLIC) =====
    @GetMapping("/product-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    // ===== DELETE CATEGORY =====
    @DeleteMapping("/admin/product-categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    // ===== UPLOAD CATEGORY IMAGE =====
    @PostMapping("/admin/product-categories/upload-image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadCategoryImage(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = fileStorageService.uploadCategoryImage(file);
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
