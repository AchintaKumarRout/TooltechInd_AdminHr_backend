package com.tooltechind.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "specification_definitions",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sub_category_id", "spec_key"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;

    @Column(name = "spec_key", nullable = false)
    private String specKey;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}
