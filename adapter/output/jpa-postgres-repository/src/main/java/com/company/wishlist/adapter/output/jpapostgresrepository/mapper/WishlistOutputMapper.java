package com.company.wishlist.adapter.output.jpapostgresrepository.mapper;

import com.company.wishlist.adapter.output.jpapostgresrepository.entity.CustomerEntity;
import com.company.wishlist.adapter.output.jpapostgresrepository.entity.WishlistEntity;
import com.company.wishlist.core.wishlist.Wishlist;

import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class WishlistOutputMapper {

    public List<WishlistEntity> toEntity(final Wishlist wishList, final CustomerEntity customerEntity) {
        return wishList.products().stream()
                .map(product -> WishlistEntity.builder()
                        .customer(customerEntity)
                        .externalProductId(product.id().value())
                        .build())
                .collect(toList());
    }
}
