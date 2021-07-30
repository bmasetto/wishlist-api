package com.company.wishlist.core.product;

import java.util.Objects;

public class Product {

    private final ProductId id;
    private final String title;
    private final String image;
    private final Double price;
    private final Double reviewScore;

    public static Product from(ProductId id, String title, String image, Double price, Double reviewScore) {
        return new Product(id, title, image, price, reviewScore);
    }

    public ProductId id() {
        return getId();
    }

    public String title() {
        return getTitle();
    }

    public String image() {
        return getImage();
    }

    public Double price() {
        return getPrice();
    }

    public Double reviewScore() {
        return getReviewScore();
    }

    private Product(ProductId id, String title, String image, Double price, Double reviewScore) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.reviewScore = reviewScore;
    }

    private ProductId getId() {
        return id;
    }

    private String getTitle() {
        return title;
    }

    private String getImage() {
        return image;
    }

    private Double getPrice() {
        return price;
    }

    private Double getReviewScore() {
        return reviewScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
