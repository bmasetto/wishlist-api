package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;

import java.util.regex.Pattern;

class CustomerEmail {

    private static final Pattern VALID_EMAIL_REGEX_PATTERN = Pattern
            .compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"); //According to RFC 5322

    private final String value;

    private CustomerEmail(String value) {
        this.value = value;
    }

    static CustomerEmail from(String email) {

        if (!VALID_EMAIL_REGEX_PATTERN.matcher(email).matches()) {
            throw new InvalidDataException("Email is invalid: " + email);
        }

        return new CustomerEmail(email.trim());
    }

    String value() {
        return value;
    }

}
