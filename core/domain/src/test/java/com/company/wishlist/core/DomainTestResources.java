package com.company.wishlist.core;

import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;

public class DomainTestResources {

    public static Customer john() {
        return Customer.from(CustomerId.from(1L), "john@email.com", "John");
    }

    public static Product tv() {
        return Product.from(ProductId.from("123"), "TV", "tv.jpg", 10.0, 1.0);
    }

    public static Product cellphone() {
        return Product.from(ProductId.from("456"), "Cellphone", "cellphone.jpg", 20.0, 2.0);
    }

}
