package com.tooltechind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private long employees;
    private long products;
    private long categories;
    private long subCategories;
}