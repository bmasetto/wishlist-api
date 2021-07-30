package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.entity.CachedCustomerEntity;
import com.company.wishlist.adapter.output.rediscache.entity.CachedProductEntity;
import com.company.wishlist.adapter.output.rediscache.entity.CachedWishlistEntity;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.wishlist.Wishlist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisOutputTestResources {

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

    public static CachedCustomerEntity johnEntity() {
        return CachedCustomerEntity.builder()
                .id(johnId().value())
                .email(john().email())
                .name(john().name())
                .build();
    }

    public static CachedProductEntity tvEntity() {
        return CachedProductEntity.builder()
                .id(tvId().value())
                .title(tv().title())
                .image(tv().image())
                .price(tv().price())
                .reviewScore(tv().reviewScore())
                .build();
    }

    public static CachedWishlistEntity johnWishlistEntity() {
        return CachedWishlistEntity.builder()
                .customerId(johnEntity().getId())
                .customer(johnEntity())
                .products(Set.of(tvEntity()))
                .build();
    }

}
