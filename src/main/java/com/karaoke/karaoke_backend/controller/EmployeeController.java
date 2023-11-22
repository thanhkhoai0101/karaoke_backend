package com.karaoke.karaoke_backend.controller;

import com.karaoke.karaoke_backend.dto.EmployeeRequest;
import com.karaoke.karaoke_backend.dto.EmployeeResponse;
import com.karaoke.karaoke_backend.model.Employee;
import com.karaoke.karaoke_backend.repository.EmployeePagingAndSortingRepository;
import com.karaoke.karaoke_backend.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@Tag(name = "Employee Controller")
public class EmployeeController {
    private final EmployeePagingAndSortingRepository employeePagingAndSortingRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    public EmployeeController(EmployeePagingAndSortingRepository employeePagingAndSortingRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeePagingAndSortingRepository = employeePagingAndSortingRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public Page<EmployeeResponse> list(
            @Parameter(hidden = true) Pageable pageable) {
        return employeePagingAndSortingRepository.findAll(pageable).map(Employee::toEmployeeResponse);
    }

    @GetMapping("{username}")
    public EmployeeResponse get(@PathVariable String username) {
        return employeeRepository.findByUsername(username).get().toEmployeeResponse();
    }

    @PostMapping("/register")
    public EmployeeResponse register(@RequestBody EmployeeRequest request) {
        if (employeeRepository.findByUsername(request.getUsername()).isPresent()) {
            return null;
        }

        Employee employee = new Employee();

        employee.setUsername(request.getUsername());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAvatar(request.getAvatar());
        employee.setFullName(request.getFullName());

        return employeeRepository.save(employee).toEmployeeResponse();
    }

    @PutMapping("{id}")
    public EmployeeResponse update(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        if (employeeRepository.findById(id).isEmpty()) {
            return null;
        }
        Employee employee = employeeRepository.findById(id).get();

        employee.setUsername(request.getUsername());
        employee.setPassword(request.getPassword());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAvatar(request.getAvatar());
        employee.setFullName(request.getFullName());

        return employeeRepository.save(employee).toEmployeeResponse();
    }
}
