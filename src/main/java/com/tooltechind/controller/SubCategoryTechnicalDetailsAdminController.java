package com.tooltechind.controller;



import com.tooltechind.entity.SubCategoryTechnicalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tooltechind.service.SubCategoryTechnicalDetailsService;

@RestController
@RequestMapping("/api/admin/subcategory-technical-details")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubCategoryTechnicalDetailsAdminController {

    private final SubCategoryTechnicalDetailsService service;


    @PostMapping("/{subCategoryId}")
    public SubCategoryTechnicalDetails saveOrUpdate(
            @PathVariable Long subCategoryId,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) MultipartFile drawing,
            @RequestParam(required = false) String description) {

        return service.saveOrUpdate(
                subCategoryId,
                image,
                drawing,
                description
        );
    }


    @GetMapping("/{subCategoryId}")
    public SubCategoryTechnicalDetails get(
            @PathVariable Long subCategoryId) {

        return service.getBySubCategoryId(subCategoryId);
    }

}
