package com.company.wishlist.core.wishlist;

import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import com.company.wishlist.core.repository.WishlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetWishlist {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    GetWishlist(@Qualifier("cached") final WishlistRepository wishlistRepository,
                @Qualifier("cached") final CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    public Wishlist getBy(final CustomerId customerId) {

        var customer = customerRepository.findBy(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId.value()));

        return wishlistRepository.getBy(customer);
    }
}
