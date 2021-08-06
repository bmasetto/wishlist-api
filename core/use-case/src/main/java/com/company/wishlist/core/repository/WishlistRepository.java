package com.company.wishlist.core.repository;

import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.wishlist.Wishlist;

public interface WishlistRepository {

    void save(Wishlist wishList);

    Wishlist getBy(Customer customer);

}
