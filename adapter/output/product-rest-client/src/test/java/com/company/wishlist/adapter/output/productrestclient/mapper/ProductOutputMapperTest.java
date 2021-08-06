package com.company.wishlist.adapter.output.productrestclient.mapper;

import org.junit.jupiter.api.Test;

import static com.company.wishlist.adapter.output.productrestclient.RestClientOutputTestResources.tvDTO;
import static org.assertj.core.api.Assertions.assertThat;

class ProductOutputMapperTest {

    @Test
    void shouldMapIdWhenConvertedToDomain() {
        var product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.id().value()).isEqualTo(tvDTO().getId());
    }

    @Test
    void shouldMapTitleWhenConvertedToDomain() {
        var product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.title()).isEqualTo(tvDTO().getTitle());
    }

    @Test
    void shouldMapImageWhenConvertedToDomain() {
        var product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.image()).isEqualTo(tvDTO().getImage());
    }

    @Test
    void shouldMapPriceWhenConvertedToDomain() {
        var product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.price()).isEqualTo(tvDTO().getPrice());
    }

    @Test
    void shouldMapReviewScoreWhenConvertedToDomain() {
        var product = new ProductOutputMapper().toDomain(tvDTO());
        assertThat(product.reviewScore()).isEqualTo(tvDTO().getReviewScore());
    }

}
