package com.tooltechind.service;


import com.tooltechind.dto.DashboardStatsDTO;
import com.tooltechind.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final EmployeeRepository employeeRepository;

    public DashboardStatsDTO getStats() {
        return new DashboardStatsDTO(
            employeeRepository.count(),
            productRepository.count(),
            categoryRepository.count(),
            subCategoryRepository.count()
        );
    }
}
