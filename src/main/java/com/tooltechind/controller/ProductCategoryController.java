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
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProductCategoryController {

	@Autowired
	private FileStorageService fileStorageService;
    @Autowired
    private ProductCategoryService service;

    @PostMapping("/admin/product-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryDTO> createCategory(
            @RequestBody ProductCategoryDTO dto) {
        return ResponseEntity.ok(service.createCategory(dto));
    }

    @PutMapping("/admin/product-categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody ProductCategoryDTO dto) {
        return ResponseEntity.ok(service.updateCategory(id, dto));
    }

    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCategoryById(id));
    }

    @GetMapping("/admin/product-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @DeleteMapping("/admin/product-categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/admin/product-categories/upload-image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadCategoryImage(
            @RequestParam("file") MultipartFile file) {

        String imageUrl = fileStorageService.uploadCategoryImage(file);

        return ResponseEntity.ok(
                Map.of("imageUrl", imageUrl)
        );
    }
}
