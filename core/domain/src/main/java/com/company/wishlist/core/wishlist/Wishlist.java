package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Wishlist {

    private final Customer customer;
    private final Set<Product> products;

    public static Wishlist from(Customer customer, Set<Product> products) {

        if (customer == null) {
            throw new InvalidDataException("Customer should not be null");
        }

        if (products == null) {
            throw new InvalidDataException("Products should not be null");
        }

        return new Wishlist(customer, products);
    }

    public Customer customer() {
        return getCustomer();
    }

    public Set<Product> products() {
        return getProducts();
    }

    void add(List<Product> incomingProducts) {

        var duplicatedIncomingProducts = incomingProducts.stream()
                .filter(products::contains)
                .map(Product::id)
                .collect(toList());

        if (!duplicatedIncomingProducts.isEmpty()) {
            throw new InvalidDataException("Products already exists: " + duplicatedIncomingProducts);
        }

        products.addAll(incomingProducts);
    }

    void delete(List<ProductId> productsIdsToBeDeleted) {
        products.removeIf(product ->
                productsIdsToBeDeleted.stream()
                        .anyMatch(productToBeDeleted -> productToBeDeleted.equals(product.id()))
        );
    }

    private Wishlist(Customer customer, Set<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    private Customer getCustomer() {
        return customer;
    }

    private Set<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var wishlist = (Wishlist) o;
        return customer.equals(wishlist.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer);
    }
}
