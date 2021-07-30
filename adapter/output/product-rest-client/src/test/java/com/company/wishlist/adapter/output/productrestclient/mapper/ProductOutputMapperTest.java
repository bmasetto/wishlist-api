package com.company.wishlist.adapter.output.productrestclient.mapper;

import com.company.wishlist.core.product.Product;
import org.junit.jupiter.api.Test;

import static com.company.wishlist.adapter.output.productrestclient.mapper.RestClientOutputTestResources.tvDTO;
import static org.assertj.core.api.Assertions.assertThat;

class ProductOutputMapperTest {

    @Test
    void shouldMapIdWhenConvertedToDomain() {
        Product product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.id().value()).isEqualTo(tvDTO().getId());
    }

    @Test
    void shouldMapTitleWhenConvertedToDomain() {
        Product product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.title()).isEqualTo(tvDTO().getTitle());
    }

    @Test
    void shouldMapImageWhenConvertedToDomain() {
        Product product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.image()).isEqualTo(tvDTO().getImage());
    }

    @Test
    void shouldMapPriceWhenConvertedToDomain() {
        Product product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.price()).isEqualTo(tvDTO().getPrice());
    }

    @Test
    void shouldMapReviewScoreWhenConvertedToDomain() {
        Product product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.reviewScore()).isEqualTo(tvDTO().getReviewScore());
    }

}