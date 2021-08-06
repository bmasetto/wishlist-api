package com.company.wishlist.adapter.output.rediscache.mapper;

import com.company.wishlist.adapter.output.rediscache.entity.CachedWishlistEntity;
import com.company.wishlist.core.wishlist.Wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
public class CachedWishlistMapper {

    private final CachedProductMapper cachedProductMapper;
    private final CachedCustomerMapper cachedCustomerMapper;

    @Autowired
    public CachedWishlistMapper(
            final CachedProductMapper cachedProductMapper,
            final CachedCustomerMapper cachedCustomerMapper) {
        this.cachedProductMapper = cachedProductMapper;
        this.cachedCustomerMapper = cachedCustomerMapper;
    }

    public Wishlist toDomain(final CachedWishlistEntity cachedWishlistEntity) {

        var customer = cachedCustomerMapper.toDomain(cachedWishlistEntity.getCustomer());

        var products = cachedWishlistEntity.getProducts().stream()
                .map(cachedProductMapper::toDomain)
                .collect(toSet());

        return Wishlist.from(customer, products);
    }

    public CachedWishlistEntity toEntity(final Wishlist wishlist) {

        var cachedProductEntities = wishlist.products().stream()
                .map(product -> cachedProductMapper.toEntity(product.id(), product))
                .collect(toSet());

        return CachedWishlistEntity.builder()
                .customerId(wishlist.customer().id())
                .customer(cachedCustomerMapper.toEntity(wishlist.customer()))
                .products(cachedProductEntities)
                .build();
    }
}
