package com.tooltechind.controller;

import com.tooltechind.dto.ProductCreateRequest;
import com.tooltechind.dto.ProductDTO;
import com.tooltechind.dto.ProductTableDTO;
import com.tooltechind.dto.ProductWithSpecsDTO;
import com.tooltechind.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* ================= CREATE ================= */
    @PostMapping("/admin/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    /* ================= UPDATE ================= */
    @PutMapping("/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    /* ================= UPDATE SPECIFICATIONS ONLY ================= */
    @PutMapping("/admin/products/{id}/specifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProductSpecifications(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> specifications) {
        return ResponseEntity.ok(productService.updateProductSpecs(id, specifications));
    }

    /* ================= GET PRODUCT WITH SPECIFICATIONS ================= */
    @GetMapping("/admin/products/{id}/specifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductWithSpecsDTO> getProductWithSpecs(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductWithSpecs(id));
    }

    /* ================= DELETE ================= */
    @DeleteMapping("/admin/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /* ================= GET BY ID ================= */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /* ================= GET ALL (ADMIN) ================= */
    @GetMapping("/admin/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /* ================= PRODUCTS BY SUBCATEGORY ================= */
    @GetMapping("/products/sub-category/{subCategoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(productService.getProductsBySubCategory(subCategoryId));
    }

    /* ================= PRODUCTS BY CATEGORY ================= */
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    /* ================= TABLE VIEW ================= */
    @GetMapping("/products/table/sub-category/{subCategoryId}")
    public ResponseEntity<List<ProductTableDTO>> getProductsTableBySubCategory(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(productService.getProductsTableBySubCategory(subCategoryId));
    }

    /* ================= SEARCH ================= */
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String q) {
        return ResponseEntity.ok(productService.searchProducts(q));
    }

    /* ================= PAGINATION ================= */
    @GetMapping("/products/paginated")
    public ResponseEntity<Page<ProductDTO>> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productCode") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(productService.getProductsPaginated(pageable));
    }
}