package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DeleteCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    DeleteCustomer(@Qualifier("cached") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void delete(CustomerId customerId) {

        var customer = customerRepository.findBy(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId.value()));

        customerRepository.delete(customer);
    }
}
