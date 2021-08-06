package com.company.wishlist.adapter.input.springrestcontroller;

import com.company.wishlist.adapter.input.springrestcontroller.mapper.ProductInputMapper;
import com.company.wishlist.adapter.input.springrestcontroller.mapper.WishListInputMapper;
import com.company.wishlist.core.wishlist.AddProductsToWishList;
import com.company.wishlist.core.wishlist.DeleteProductsFromWishList;
import com.company.wishlist.core.wishlist.GetWishlist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.company.wishlist.adapter.input.springrestcontroller.InputTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private AddProductsToWishList addProductsToWishList;

    @Mock
    private GetWishlist getWishlist;

    @Mock
    private DeleteProductsFromWishList deleteProductFromWishlist;

    @Spy
    private WishListInputMapper wishListInputMapper = new WishListInputMapper(new ProductInputMapper());

    @Spy
    private ProductInputMapper productInputMapper;

    @Test
    void shouldReturnWishlistWhenPostingProductToWishlist() {

        when(addProductsToWishList.add(johnId(), List.of(tv().id())))
                .thenReturn(johnWishlist());

        var response = wishlistController.postProductToWishlist(
                john().id(),
                List.of(incomingTvDTO())
        );

        assertThat(response.getBody()).isEqualTo(johnWishlistDTO());
    }

    @Test
    void shouldReturnStatusWhenPostingToWishlist() {

        when(addProductsToWishList.add(johnId(), List.of(tv().id())))
                .thenReturn(johnWishlist());

        var response = wishlistController.postProductToWishlist(
                john().id(),
                List.of(incomingTvDTO())
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnWishlistWhenGettingWishlistByCustomerId() {

        when(getWishlist.getBy(johnId()))
                .thenReturn(johnWishlist());

        var response = wishlistController.getWishlist(johnId().value());

        assertThat(response.getBody()).isEqualTo(johnWishlistDTO());
    }

    @Test
    void shouldReturnStatusCodeWhenGettingWishlistByCustomerId() {

        when(getWishlist.getBy(johnId()))
                .thenReturn(johnWishlist());

        var response = wishlistController.getWishlist(johnId().value());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnWishlistWhenDeletingProductFromWishlist() {

        when(deleteProductFromWishlist.delete(johnId(), List.of(cellphone().id())))
                .thenReturn(johnWishlist());

        var response = wishlistController.deleteProductFromWishlist(
                johnId().value(),
                List.of(incomingCellphoneDTO())
        );

        assertThat(response.getBody()).isEqualTo(johnWishlistDTO());
    }

    @Test
    void shouldReturnStatusCodeWhenDeletingProductFromWishlist() {

        when(deleteProductFromWishlist.delete(johnId(), List.of(cellphone().id())))
                .thenReturn(johnWishlist());

        var response = wishlistController.deleteProductFromWishlist(
                johnId().value(),
                List.of(incomingCellphoneDTO())
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
