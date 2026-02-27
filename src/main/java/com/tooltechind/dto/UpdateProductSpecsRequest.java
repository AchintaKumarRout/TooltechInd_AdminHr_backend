package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductSpecsRequest {
    private Long productId;
    // Map of specDefinitionId -> specValue
    private Map<Long, String> specifications;
}
