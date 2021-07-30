package com.company.wishlist.core.repository;

import com.company.wishlist.core.wishlist.Wishlist;
import com.company.wishlist.core.customer.Customer;

public interface WishlistRepository {

    void save(Wishlist wishList);

    Wishlist getBy(Customer customer);

}
