package com.company.wishlist.adapter.output.rediscache.mapper;

import com.company.wishlist.adapter.output.rediscache.entity.CachedProductEntity;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;

import org.springframework.stereotype.Component;

@Component
public class CachedProductMapper {

    public Product toDomain(final CachedProductEntity cachedProductEntity) {
        return Product.from(
                ProductId.from(cachedProductEntity.getId()),
                cachedProductEntity.getTitle(),
                cachedProductEntity.getImage(),
                cachedProductEntity.getPrice(),
                cachedProductEntity.getReviewScore()
        );
    }

    public CachedProductEntity toEntity(final ProductId productId, final Product product) {
        return CachedProductEntity.builder()
                .id(productId.value())
                .title(product.title())
                .image(product.image())
                .price(product.price())
                .reviewScore(product.reviewScore())
                .build();
    }

}
