package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.company.wishlist.core.customer.UseCaseTestResources.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductsFromWishListTest {

    @InjectMocks
    private DeleteProductsFromWishList deleteProductsFromWishList;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldDeleteProductFromWishlist() {
        var mockedWishlist = mock(Wishlist.class);

        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        when(wishlistRepository.getBy(john()))
                .thenReturn(mockedWishlist);

        deleteProductsFromWishList.delete(johnId(), List.of(tv().id()));

        verify(wishlistRepository, times(1)).save(mockedWishlist);
        verify(mockedWishlist, times(1)).delete(List.of(tv().id()));
    }

    @Test
    void shouldThrowErrorWhenCustomerDoesNotExist() {
        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteProductsFromWishList.delete(johnId(), List.of(tv().id())))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");
    }

}
