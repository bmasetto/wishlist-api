package com.company.wishlist.app.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.company.wishlist"})
public class WishlistApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishlistApiApplication.class, args);
    }

}
