package com.company.wishlist.adapter.output.jpapostgresrepository.mapper;

import com.company.wishlist.adapter.output.jpapostgresrepository.entity.CustomerEntity;
import com.company.wishlist.adapter.output.jpapostgresrepository.entity.WishlistEntity;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.wishlist.Wishlist;

import java.util.HashSet;
import java.util.List;

public class JPAOutputTestResources {

    public static Customer john() {
        return Customer.from(CustomerId.from(1L), "john@email.com", "Teste");
    }

    public static CustomerId johnId() {
        return CustomerId.from(john().id());
    }

    public static Product tv() {
        return Product.from(ProductId.from("123"), "TV", "tv.jpg", 10.0, 1.0);
    }

    public static ProductId tvId() {
        return tv().id();
    }

    public static Wishlist johnWishlist() {
        return Wishlist.from(john(), new HashSet<>(List.of(tv())));
    }

    public static CustomerEntity johnEntity() {
        return CustomerEntity.builder()
                .id(johnId().value())
                .email(john().email())
                .name(john().name())
                .build();
    }

    public static WishlistEntity johnWishlistEntity() {
        return WishlistEntity.builder()
                .id(10L)
                .customer(johnEntity())
                .externalProductId(tvId().value())
                .build();
    }

}
