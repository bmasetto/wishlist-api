package com.company.wishlist.core.product;

import com.company.wishlist.core.exception.InvalidDataException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductIdTest {

    @Test
    void shouldReturnValueWhenProductIdIsCreated() {
        var productId = ProductId.from("123");

        assertThat(productId.value()).isEqualTo("123");
    }

    @Test
    void shouldThrowErrorWhenProductIdIsCreatedWithNullValue() {
        assertThatThrownBy(() -> ProductId.from(null))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Product id should not be null or blank");
    }

    @Test
    void shouldThrowErrorWhenProductIdIsCreatedWithBlankValue() {
        assertThatThrownBy(() -> ProductId.from(" "))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Product id should not be null or blank");
    }

}
