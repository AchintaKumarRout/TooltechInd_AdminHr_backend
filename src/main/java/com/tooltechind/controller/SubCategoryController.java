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
@CrossOrigin(origins = {"http://localhost:3000"})
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private FileStorageService fileStorageService;

    // ===== CREATE =====
    @PostMapping("/admin/sub-categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> createSubCategory(@RequestBody SubCategoryDTO dto) {
        return ResponseEntity.ok(subCategoryService.createSubCategory(dto));
    }

    // ===== UPDATE =====
    @PutMapping("/admin/sub-categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(
            @PathVariable Long id,
            @RequestBody SubCategoryDTO dto) {
        return ResponseEntity.ok(subCategoryService.updateSubCategory(id, dto));
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

//    // ===== UPLOAD IMAGE =====
//    @PostMapping("/admin/sub-categories/upload-image")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Map<String, String>> uploadSubCategoryImage(
//            @RequestParam("file") MultipartFile file) {
//
//        String imageUrl = fileStorageService.uploadSubCategoryImage(file); // implement this in your service
//        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
//    }
}
