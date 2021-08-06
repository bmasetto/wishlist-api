package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;

import java.util.Objects;

public class Customer {

    private final CustomerId id;
    private CustomerEmail email;
    private CustomerName name;

    public static Customer from(final CustomerId customerId, final String email, final String name) {

        if (customerId == null) {
            throw new InvalidDataException("Customer id should not be blank");
        }

        return new Customer(
                customerId,
                CustomerEmail.from(email),
                CustomerName.from(name)
        );
    }

    public Long id() {
        return getId().value();
    }

    public String email() {
        return getEmail().value();
    }

    public String name() {
        return getName().getValue();
    }

    public void update(final CustomerEmail email, final CustomerName name) {

        if (email == null) {
            throw new InvalidDataException("Email should not be null");
        }

        if (name == null) {
            throw new InvalidDataException("Name should not be null");
        }

        this.email = email;
        this.name = name;
    }

    private Customer(final CustomerId id, final CustomerEmail email, final CustomerName name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    CustomerId getId() {
        return id;
    }

    CustomerEmail getEmail() {
        return email;
    }

    CustomerName getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
