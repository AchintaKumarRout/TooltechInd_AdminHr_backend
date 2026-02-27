//package com.tooltechind.controller;
//
//
//import com.tooltechind.dto.DashboardStatsDTO;
//import com.tooltechind.service.DashboardService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/dashboard")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//
//public class DashboardController {
//
//    private final DashboardService dashboardService;
//
//    @GetMapping("/stats")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ProductController.ApiResponse<DashboardStatsDTO>> getStats() {
//        return ResponseEntity.ok(
//            ProductController.ApiResponse.success(dashboardService.getStats())
//        );
//    }
//}
