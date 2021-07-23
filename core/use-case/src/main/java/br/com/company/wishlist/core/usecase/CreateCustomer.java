package br.com.company.wishlist.core.usecase;

import br.com.company.wishlist.core.domain.Customer;
import br.com.company.wishlist.core.usecase.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomer {

    private final CustomerRepository customerRepository;

    @Autowired
    public CreateCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer incomingCustomer) {
        return customerRepository.create(incomingCustomer);
    }
}
