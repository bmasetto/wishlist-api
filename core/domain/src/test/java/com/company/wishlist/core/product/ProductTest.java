package com.company.wishlist.core.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void shouldReturnProductIdWhenProductIsCreated() {
        ProductId from = ProductId.from("123");
        String tv = "TV";
        String image = "tv.jpg";
        Double price = 10.0;
        Double reviewScore = 1.0;

        var product = Product.from(from, tv, image, price, reviewScore);

        assertThat(product.id()).isEqualTo(ProductId.from("123"));
    }

    @Test
    void shouldReturnTitleWhenProductIsCreated() {
        ProductId from = ProductId.from("123");
        String tv = "TV";
        String image = "tv.jpg";
        Double price = 10.0;
        Double reviewScore = 1.0;

        var product = Product.from(from, tv, image, price, reviewScore);

        assertThat(product.title()).isEqualTo("TV");
    }

    @Test
    void shouldReturnImageWhenProductIsCreated() {
        ProductId from = ProductId.from("123");
        String tv = "TV";
        String image = "tv.jpg";
        Double price = 10.0;
        Double reviewScore = 1.0;

        var product = Product.from(from, tv, image, price, reviewScore);

        assertThat(product.image()).isEqualTo("tv.jpg");
    }

    @Test
    void shouldReturnPriceWhenProductIsCreated() {
        ProductId from = ProductId.from("123");
        String tv = "TV";
        String image = "tv.jpg";
        Double price = 10.0;
        Double reviewScore = 1.0;

        var product = Product.from(from, tv, image, price, reviewScore);

        assertThat(product.price()).isEqualTo(10.0);
    }

    @Test
    void shouldReturnReviewScoreWhenProductIsCreated() {
        ProductId from = ProductId.from("123");
        String tv = "TV";
        String image = "tv.jpg";
        Double price = 10.0;
        Double reviewScore = 1.0;

        var product = Product.from(from, tv, image, price, reviewScore);

        assertThat(product.reviewScore()).isEqualTo(1.0);
    }

}
