package com.karaoke.karaoke_backend.repository;

import com.karaoke.karaoke_backend.dto.CustomerResponse;
import com.karaoke.karaoke_backend.model.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> findById(Long id);

    Customer save(Customer customer);
}
