package com.company.wishlist.core.customer;

import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.wishlist.Wishlist;

import java.util.HashSet;
import java.util.List;

public class UseCaseTestResources {

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

}
