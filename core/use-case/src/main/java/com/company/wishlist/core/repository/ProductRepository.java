package com.company.wishlist.core.repository;

import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findBy(ProductId productId);

}
