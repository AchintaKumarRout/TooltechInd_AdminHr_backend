package com.tooltechind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_specification_values")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "spec_definition_id", nullable = false)
    private Long specDefinitionId;
    
    @Column(name = "spec_value", length = 500)
    private String specValue;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spec_definition_id", insertable = false, updatable = false)
    private SpecificationDefinition specificationDefinition;
}