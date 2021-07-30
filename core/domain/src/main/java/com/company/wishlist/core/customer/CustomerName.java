package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;

class CustomerName {

    private final String value;

    static CustomerName from(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name should not be blank");
        }

        return new CustomerName(name.trim());
    }

    private CustomerName(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }

}
