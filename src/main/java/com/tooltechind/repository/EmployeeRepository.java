package com.tooltechind.repository;



import com.tooltechind.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    boolean existsByEmail(String email);
    
    @Query("SELECT e FROM Employee e WHERE LOWER(e.email) = LOWER(:email)")
    Optional<Employee> findByEmailIgnoreCase(String email);
    
    Optional<Employee> findByEmail(String email);
}
