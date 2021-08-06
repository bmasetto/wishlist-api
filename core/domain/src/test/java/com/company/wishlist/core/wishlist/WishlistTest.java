package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.product.Product;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.company.wishlist.core.DomainTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WishlistTest {

    @Test
    void shouldReturnCustomerWhenWishlistIsCreated() {
        var wishlist = Wishlist.from(john(), Set.of(tv(), cellphone()));

        assertThat(wishlist.customer()).isEqualTo(john());
    }

    @Test
    void shouldReturnProductsWhenWishlistIsCreated() {
        var wishlist = Wishlist.from(john(), Set.of(tv(), cellphone()));

        assertThat(wishlist.products()).isEqualTo(Set.of(tv(), cellphone()));
    }

    @Test
    void shouldThrowErrorWhenWishlistIsCreatedWithNullCustomer() {
        Customer customer = null;

        assertThatThrownBy(() -> Wishlist.from(customer, Set.of(tv(), cellphone())))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Customer should not be null");
    }

    @Test
    void shouldThrowErrorWhenWishlistIsCreatedWithNullProducts() {
        Set<Product> products = null;

        assertThatThrownBy(() -> Wishlist.from(john(), products))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Products should not be null");
    }

    @Test
    void shouldReturnUpdatedProductsWhenProductsAreAdded() {
        var wishlist = Wishlist.from(john(), new HashSet<>(List.of(tv())));

        wishlist.add(List.of(cellphone()));

        assertThat(wishlist.products()).isEqualTo(Set.of(tv(), cellphone()));
    }

    @Test
    void shouldThrowErrorWhenDuplicatedProductIsAddedToWishlist() {
        var wishlist = Wishlist.from(john(), new HashSet<>(List.of(tv())));

        assertThatThrownBy(() -> wishlist.add(List.of(tv())))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageStartingWith("Products already exists");
    }

    @Test
    void shouldReturnUpdatedProductsWhenProductsAreDeleted() {
        var wishlist = Wishlist.from(john(), new HashSet<>(List.of(tv(), cellphone())));

        wishlist.delete(List.of(cellphone().id()));

        assertThat(wishlist.products()).isEqualTo(Set.of(tv()));
    }

}
