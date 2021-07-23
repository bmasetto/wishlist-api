package br.com.company.wishlist.core.usecase.repository;

import br.com.company.wishlist.core.domain.Customer;

public interface CustomerRepository {

    Customer create(Customer customer);

}
