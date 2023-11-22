package com.karaoke.karaoke_backend.controller;

import com.karaoke.karaoke_backend.dto.LoginRequest;
import com.karaoke.karaoke_backend.dto.LoginResponse;
import com.karaoke.karaoke_backend.model.Employee;
import com.karaoke.karaoke_backend.model.LoginSession;
import com.karaoke.karaoke_backend.repository.EmployeePagingAndSortingRepository;
import com.karaoke.karaoke_backend.repository.EmployeeRepository;
import com.karaoke.karaoke_backend.repository.LoginSessionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final LoginSessionRepository loginSessionRepository;

    public AuthController(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, LoginSessionRepository loginSessionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.loginSessionRepository = loginSessionRepository;
    }

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Optional<Employee> user = employeeRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            return null;
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return null;
        }
        String token = UUID.randomUUID().toString();
        LoginSession session = new LoginSession();
        session.setEmployee(user.get());
        session.setToken(token);
        loginSessionRepository.save(session);

        return LoginResponse.builder()
                .token(token)
                .employee(session.getEmployee().toEmployeeResponse())
                .build();
    }

    @GetMapping("check-login")
    public boolean checkLogin() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Employee) {
            System.out.println("zô rồi!!");
            return true;
        }
        System.out.println("Thoát");
        return false;

    }

    @DeleteMapping("logout")
    public void logout(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");

        if (token != null) {
            String rawToken = token.substring("Bearer ".length());
            loginSessionRepository.deleteById(loginSessionRepository.findByToken(rawToken).get().getId());
        }
    }
}
