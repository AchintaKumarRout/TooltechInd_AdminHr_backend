package com.tooltechind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "specification_definitions", indexes = {
    @Index(name = "idx_spec_def_subcategory", columnList = "sub_category_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationDefinition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sub_category_id", nullable = false)
    private Long subCategoryId;
    
    @Column(name = "spec_key", nullable = false, length = 100)
    private String specKey;
    
    @Column(name = "spec_label", length = 100)
    private String specLabel;
    
    @Column(name = "spec_unit", length = 50)
    private String specUnit;
    
    @Column(name = "data_type", length = 20)
    private String dataType; // decimal, number, text
    
    @Column(name = "is_required")
    private Boolean isRequired = false;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", insertable = false, updatable = false)
    private SubCategory subCategory;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}