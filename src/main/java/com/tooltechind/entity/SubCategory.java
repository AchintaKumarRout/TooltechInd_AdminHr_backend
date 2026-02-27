package com.tooltechind.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sub_categories")
@Data
@ToString(exclude = "productCategory")          // Avoid LazyInitializationException in toString
@EqualsAndHashCode(exclude = "productCategory") // Avoid issues in sets/maps
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sub_category_name", nullable = false)
    private String subCategoryName;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;

}
