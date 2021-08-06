package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.ProductRepository;
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
class AddProductsToWishListTest {

    @InjectMocks
    private AddProductsToWishList addProductsToWishList;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldAddProdutoToWishlist() {
        var mockedWishlist = mock(Wishlist.class);

        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        when(productRepository.findBy(cellphone().id()))
                .thenReturn(Optional.of(cellphone()));

        when(wishlistRepository.getBy(john()))
                .thenReturn(mockedWishlist);

        addProductsToWishList.add(johnId(), List.of(cellphone().id()));

        verify(wishlistRepository, times(1)).save(mockedWishlist);
        verify(mockedWishlist, times(1)).add(List.of(cellphone()));
    }

    @Test
    void shouldThrowErrorWhenCustomerDoesNotExist() {
        when(customerRepository.findBy(CustomerId.from(999L)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> addProductsToWishList.add(CustomerId.from(999L), List.of(cellphone().id())))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");
    }

    @Test
    void shouldThrowErrorWhenAddedProductsDoNotExist() {
        var notExistedProduct = cellphone().id();

        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        when(productRepository.findBy(notExistedProduct))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> addProductsToWishList.add(johnId(), List.of(notExistedProduct)))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageStartingWith("Product not found");
    }

}
