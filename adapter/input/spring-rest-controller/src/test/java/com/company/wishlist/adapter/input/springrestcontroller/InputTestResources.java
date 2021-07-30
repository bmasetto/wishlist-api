package com.company.wishlist.adapter.input.springrestcontroller;

import com.company.wishlist.adapter.input.springrestcontroller.dto.*;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.wishlist.Wishlist;

import java.util.HashSet;
import java.util.List;

public class InputTestResources {

    public static Customer john() {
        return Customer.from(CustomerId.from(1L), "john@email.com", "John");
    }

    public static CustomerId johnId() {
        return CustomerId.from(john().id());
    }

    public static Product tv() {
        return Product.from(ProductId.from("123"), "TV", "tv.jpg", 10.0, 1.0);
    }

    public static Product cellphone() {
        return Product.from(ProductId.from("456"), "Cellphone", "cellphone.jpg", 20.0, 2.0);
    }

    public static Wishlist johnWishlist() {
        return Wishlist.from(john(), new HashSet<>(List.of(tv())));
    }

    public static IncomingCustomerDTO incomingJohnDTO() {
        return IncomingCustomerDTO.builder()
                .name(john().name())
                .email(john().email())
                .build();
    }

    public static CustomerDTO johnDTO() {
        return CustomerDTO.builder()
                .id(johnId().value())
                .email(john().email())
                .name(john().name())
                .build();
    }

    public static IncomingProductDTO incomingTvDTO() {
        return IncomingProductDTO.builder()
                .id(tv().id().value())
                .build();
    }

    public static ProductDTO tvDTO() {
        return ProductDTO.builder()
                .id(tv().id().value())
                .title(tv().title())
                .image(tv().image())
                .price(tv().price())
                .reviewScore(tv().reviewScore())
                .build();
    }

    public static IncomingProductDTO incomingCellphoneDTO() {
        return IncomingProductDTO.builder()
                .id(cellphone().id().value())
                .build();
    }

    public static WishlistDTO johnWishlistDTO() {
        return WishlistDTO.builder()
                .products(List.of(tvDTO()))
                .build();
    }

}
