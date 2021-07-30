package com.company.wishlist.core.repository;

import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

    Customer create(Customer customer);

    Optional<Customer> findBy(CustomerId customerId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndDifferentId(String email, CustomerId id);

    void update(Customer customer);

    void delete(Customer customer);
}
