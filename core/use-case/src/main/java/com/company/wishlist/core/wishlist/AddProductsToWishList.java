package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.ProductRepository;
import com.company.wishlist.core.repository.WishlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Component
public class AddProductsToWishList {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    AddProductsToWishList(@Qualifier("cached") final WishlistRepository wishlistRepository,
                          @Qualifier("cached") final CustomerRepository customerRepository,
                          final ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Wishlist add(final CustomerId customerId, final List<ProductId> productsIds) {

        var customer = customerRepository.findBy(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId.value()));

        var products = productsIds.stream()
                .map(toProductFromRepository())
                .collect(toList());

        var wishList = wishlistRepository.getBy(customer);

        wishList.add(products);

        wishlistRepository.save(wishList);

        return wishList;
    }

    private Function<ProductId, Product> toProductFromRepository() {
        return productId -> productRepository.findBy(productId)
                .orElseThrow(() -> new InvalidDataException("Product not found: " + productId.value()));
    }
}
