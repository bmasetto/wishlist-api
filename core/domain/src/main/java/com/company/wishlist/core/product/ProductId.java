package com.company.wishlist.core.product;

import com.company.wishlist.core.exception.InvalidDataException;

import java.util.Objects;

public class ProductId {

    private final String id;

    public static ProductId from(String id) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("Product id should not be null or blank");
        }

        return new ProductId(id);
    }

    private ProductId(String id) {
        this.id = id;
    }

    public String value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var productId = (ProductId) o;
        return id.equals(productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "id='" + id + '\'' +
                '}';
    }
}
