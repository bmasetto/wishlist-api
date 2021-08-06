package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.WishlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteProductsFromWishList {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    DeleteProductsFromWishList(@Qualifier("cached") final WishlistRepository wishlistRepository,
                               @Qualifier("cached") final CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    public Wishlist delete(final CustomerId customerId, final List<ProductId> productsIdsToBeDeleted) {

        var customer = customerRepository.findBy(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId.value()));

        var wishList = wishlistRepository.getBy(customer);

        wishList.delete(productsIdsToBeDeleted);

        wishlistRepository.save(wishList);

        return wishList;
    }
}
