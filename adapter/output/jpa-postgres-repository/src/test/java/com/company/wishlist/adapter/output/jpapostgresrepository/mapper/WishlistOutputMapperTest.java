package com.company.wishlist.adapter.output.jpapostgresrepository.mapper;

import org.junit.jupiter.api.Test;

import static com.company.wishlist.adapter.output.jpapostgresrepository.mapper.JPAOutputTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;

class WishlistOutputMapperTest {

    @Test
    void shouldMapCustomerWhenConvertedEntity() {
        new WishlistOutputMapper()
                .toEntity(johnWishlist(), johnEntity())
                .forEach(wishlistEntity -> assertThat(wishlistEntity.getCustomer()).isEqualTo(johnEntity()));
    }

    @Test
    void shouldMapExternalProductIdWhenConvertedEntity() {
        new WishlistOutputMapper()
                .toEntity(johnWishlist(), johnEntity())
                .forEach(wishlistEntity ->
                        assertThat(wishlistEntity.getExternalProductId())
                                .isEqualTo(johnWishlistEntity().getExternalProductId())
                );
    }

}
