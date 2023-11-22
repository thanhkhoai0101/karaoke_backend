package com.karaoke.karaoke_backend.repository;

import com.karaoke.karaoke_backend.controller.EmployeeController;
import com.karaoke.karaoke_backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}
