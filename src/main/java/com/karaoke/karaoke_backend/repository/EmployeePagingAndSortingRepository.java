package com.karaoke.karaoke_backend.repository;

import com.karaoke.karaoke_backend.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EmployeePagingAndSortingRepository extends PagingAndSortingRepository<Employee, Long> {
}
