package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerIdTest {

    @Test
    void shouldReturnValueWhenCustomerIdIsCreated() {
        var customerId = CustomerId.from(1L);

        assertThat(customerId.value()).isEqualTo(1L);
    }

    @Test
    void shouldThrowErrorWhenCustomerIdIsCreatedWithNullValue() {
        assertThatThrownBy(() -> CustomerId.from(null))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Customer id should not be null");
    }

}
