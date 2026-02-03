package com.tooltechind.service;

import com.tooltechind.dto.CreateEmployeeDTO;
import com.tooltechind.dto.EmployeeResponseDTO;
import com.tooltechind.entity.Employee;
import com.tooltechind.entity.User;
import com.tooltechind.repository.EmployeeRepository;
import com.tooltechind.repository.UserRepository;
import com.tooltechind.entity.User.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeResponseDTO createEmployee(CreateEmployeeDTO dto) {
        // Check if email exists in EITHER table
        if (employeeRepository.existsByEmail(dto.getEmail()) || 
            userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Employee/User with this email already exists");
        }
        
        // 1. Create EMPLOYEE record
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole().toUpperCase());
        employee.setDepartment(dto.getDepartment());
        String rawPassword = generateDefaultPassword();
        employee.setDefaultPassword(rawPassword);
        
        employee = employeeRepository.save(employee);
        
        // 2. Create USER record (for authentication)
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword)); // Hash password
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        userRepository.save(user);
        
        // 3. Return response
        return convertToResponseDTO(employee, rawPassword);
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, CreateEmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        String oldEmail = employee.getEmail();
        
        // Check email conflict (excluding current employee)
        if (!oldEmail.equals(dto.getEmail()) && 
            (employeeRepository.existsByEmail(dto.getEmail()) || 
             userRepository.existsByEmail(dto.getEmail()))) {
            throw new RuntimeException("Employee/User with this email already exists");
        }
        
        // Update Employee
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole().toUpperCase());
        employee.setDepartment(dto.getDepartment());
        
        employee = employeeRepository.save(employee);
        
        // Update User record
        userRepository.findByEmail(oldEmail)
            .ifPresent(user -> {
                user.setEmail(dto.getEmail());
                user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
                userRepository.save(user);
            });
        
        return convertToResponseDTO(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        // Soft delete employee
        employee.setActive(false);
        employeeRepository.save(employee);
        
        // Deactivate user account
        userRepository.findByEmail(employee.getEmail())
            .ifPresent(user -> {
                user.setRole(Role.EMPLOYEE); // Downgrade access
                userRepository.save(user);
            });
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        return convertToResponseDTO(employee);
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setRole(employee.getRole());
        dto.setDepartment(employee.getDepartment());
        dto.setActive(employee.isActive());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee, String defaultPassword) {
        EmployeeResponseDTO dto = convertToResponseDTO(employee);
        dto.setDefaultPassword(defaultPassword); // Only for create response
        return dto;
    }

    private String generateDefaultPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
    
    
    @Transactional
    public EmployeeResponseDTO updateEmployeeStatus(Long id, boolean isActive) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        employee.setActive(isActive);
        employee = employeeRepository.save(employee);
        
        // Update user role if deactivated
        if (!isActive) {
            userRepository.findByEmail(employee.getEmail())
                .ifPresent(user -> {
                    user.setRole(Role.EMPLOYEE);
                    userRepository.save(user);
                });
        }
        
        return convertToResponseDTO(employee);
    }

}
