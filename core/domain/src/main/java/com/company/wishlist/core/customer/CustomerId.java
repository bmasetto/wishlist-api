package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;

import java.util.Objects;

public class CustomerId {

    private final Long id;

    public static CustomerId from(Long id) {

        if (id == null) {
            throw new InvalidDataException("Customer id should not be null");
        }

        return new CustomerId(id);
    }

    private CustomerId(Long id) {
        this.id = id;
    }

    public Long value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (CustomerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
