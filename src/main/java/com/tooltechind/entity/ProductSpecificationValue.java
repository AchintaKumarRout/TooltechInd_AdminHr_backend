package com.tooltechind.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "product_specification_values",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "spec_definition_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificationValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_definition_id", nullable = false)
    private SpecificationDefinition specificationDefinition;

    @Column(name = "spec_value", nullable = false)
    private String specValue;
}
