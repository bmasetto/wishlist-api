package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    GetCustomer(@Qualifier("cached") final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getBy(final CustomerId customerId) {
        return customerRepository.findBy(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId.value()));
    }
}
