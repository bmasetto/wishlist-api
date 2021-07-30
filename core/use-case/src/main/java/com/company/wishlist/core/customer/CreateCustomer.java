package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    CreateCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer incomingCustomer) {

        customerRepository.findByEmail(incomingCustomer.email())
                .ifPresent(alreadyExistedCustomer -> {
                    throw new InvalidDataException("Email already exists: " + alreadyExistedCustomer.email());
                });

        return customerRepository.create(incomingCustomer);
    }
}
