package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UpdateCustomer {

    private final CustomerRepository customerRepository;

    public UpdateCustomer(@Qualifier("cached") final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer update(final Customer incomingCustomer) {

        var customer = customerRepository.findBy(incomingCustomer.getId())
                .orElseThrow(() -> new NotFoundException("Customer not found: " + incomingCustomer.id()));

        customerRepository.findByEmailAndDifferentId(incomingCustomer.email(), incomingCustomer.getId())
                .ifPresent(a -> {
                    throw new InvalidDataException("Email already exists: " + incomingCustomer.email());
                });

        customer.update(incomingCustomer.getEmail(), incomingCustomer.getName());

        customerRepository.update(customer);

        return customer;
    }

}
