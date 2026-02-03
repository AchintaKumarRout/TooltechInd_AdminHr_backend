package com.tooltechind.controller;

import java.util.Map;
import com.tooltechind.dto.CreateEmployeeDTO;
import com.tooltechind.dto.EmployeeResponseDTO;
import com.tooltechind.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("admin/employees")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @PostMapping("admin/employees")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody CreateEmployeeDTO dto) {
        EmployeeResponseDTO employee = employeeService.createEmployee(dto);
        return ResponseEntity.ok(employee);
    }
    
    @GetMapping("admin/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
    
    @PutMapping("admin/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id, 
            @Valid @RequestBody CreateEmployeeDTO dto) {
        EmployeeResponseDTO employee = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(employee);
    }
    
    @DeleteMapping("admin/employees/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("admin/employees/{id}/status")  // ← NEW ENDPOINT
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeStatus(
            @PathVariable Long id, 
            @RequestBody Map<String, Boolean> statusRequest) {
        
        boolean newStatus = statusRequest.get("active");
        EmployeeResponseDTO updated = employeeService.updateEmployeeStatus(id, newStatus);
        return ResponseEntity.ok(updated);
    }


}
