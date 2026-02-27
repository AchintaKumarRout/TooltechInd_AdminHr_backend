package com.tooltechind.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_code", columnList = "product_code"),
    @Index(name = "idx_sub_category", columnList = "sub_category_id"),
    @Index(name = "idx_shank_taper", columnList = "shank_taper"),
    @Index(name = "idx_active", columnList = "is_active")
})
@Data
@ToString(exclude = "subCategory")
@EqualsAndHashCode(exclude = "subCategory")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", unique = true, nullable = false, length = 100)
    private String productCode;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(length = 100)
    private String model;

    @Column(name = "shank_taper", length = 50)
    private String shankTaper;

    @Column(length = 50)
    private String size;

    // JSONB field for flexible specifications
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> specifications = new HashMap<>();

    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(length = 10)
    private String currency = "INR";

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "min_order_quantity")
    private Integer minOrderQuantity = 1;

    @Column(name = "lead_time_days")
    private Integer leadTimeDays;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "technical_drawing_url", length = 500)
    private String technicalDrawingUrl;

    @Column(name = "catalog_page")
    private Integer catalogPage;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_new_product")
    private Boolean isNewProduct = false;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    // Many-to-One relationship with SubCategory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods for specifications
    public void addSpecification(String key, Object value) {
        if (this.specifications == null) {
            this.specifications = new HashMap<>();
        }
        this.specifications.put(key, value);
    }

    public Object getSpecification(String key) {
        if (this.specifications == null) {
            return null;
        }
        return this.specifications.get(key);
    }

    public void removeSpecification(String key) {
        if (this.specifications != null) {
            this.specifications.remove(key);
        }
    }
}