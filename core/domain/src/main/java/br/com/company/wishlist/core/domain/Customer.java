package br.com.company.wishlist.core.domain;

import java.util.UUID;

public class Customer {

    private final String id;
    private final String email;
    private final String name;

    private Customer(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static Customer create(String email, String name) {
        return new Customer(UUID.randomUUID().toString(), email, name);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
