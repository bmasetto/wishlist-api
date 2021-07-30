package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.core.customer.UseCaseTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWishlistTest {

    @InjectMocks
    private GetWishlist getWishlist;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldReturnWishlistFromCustomer() {
        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        when(wishlistRepository.getBy(john()))
                .thenReturn(johnWishlist());

        var wishlist = getWishlist.getBy(johnId());

        assertThat(wishlist.customer().id()).isEqualTo(johnId().value());
    }

    @Test
    void shouldReturnWishlistProducts() {
        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        when(wishlistRepository.getBy(john()))
                .thenReturn(johnWishlist());

        var wishlist = getWishlist.getBy(johnId());

        assertThat(wishlist.products()).isNotEmpty();
    }

    @Test
    void shouldThrowErrorWhenCustomerDoesNotExist() {
        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getWishlist.getBy(johnId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");

    }

}
