package com.karaoke.karaoke_backend.controller;

import com.karaoke.karaoke_backend.dto.CustomerRequest;
import com.karaoke.karaoke_backend.dto.CustomerResponse;
import com.karaoke.karaoke_backend.model.Customer;
import com.karaoke.karaoke_backend.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/customers")
@Tag(name = "Khách hàng")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("")
    public Page<CustomerResponse> list(@Parameter(hidden = true) Pageable pageable) {
        return customerRepository.findAll(pageable).map(Customer::toCustomerResponse);
    }

    @GetMapping("{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id).get().toCustomerResponse();
    }

    @PostMapping("")
    public CustomerResponse createOrUpdate(@Valid @RequestBody CustomerRequest request) {
        Customer customer;

        if (Pattern.matches("\\d*", request.getId())) {
            if (customerRepository.findById(Long.parseLong(request.getId())).isEmpty()) {
                customer = new Customer();
            } else {
                customer = customerRepository.findById(Long.parseLong(request.getId())).get();
            }
        }else{
            customer = new Customer();
        }


        customer.setName(request.getName());
        customer.setAvatar(request.getAvatar());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customerRepository.save(customer).toCustomerResponse();
    }
}
