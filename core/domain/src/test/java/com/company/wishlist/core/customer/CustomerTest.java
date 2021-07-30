package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static com.company.wishlist.core.DomainTestResources.john;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @Test
    void shouldReturnCustomerIdWhenCustomerIsCreated() {
        var customerId = CustomerId.from(1L);
        var email = "test@gmail.com";
        var name = "Teste";

        var customer = Customer.from(customerId, email, name);

        assertThat(customer.getId()).isEqualTo(customerId);
    }

    @Test
    void shouldReturnEmailWhenCustomerIsCreated() {
        var customerId = CustomerId.from(1L);
        var email = "test@email.com";
        var name = "Teste";

        var customer = Customer.from(customerId, email, name);

        assertThat(customer.email()).isEqualTo(email);
    }

    @Test
    void shouldReturnNameWhenCustomerIsCreated() {
        var customerId = CustomerId.from(1L);
        var email = "test@email.com";
        var name = "Teste";

        var customer = Customer.from(customerId, email, name);

        assertThat(customer.name()).isEqualTo("Teste");
    }

    @Test
    void shouldThrowErrorWhenCustomerIsCreatedWithNullCustomerId() {
        CustomerId customerId = null;
        var email = "test@email.com";
        var name = "Teste";

        assertThatThrownBy(() -> Customer.from(customerId, email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Customer id should not be blank");
    }

    @Test
    void shouldThrowErrorWhenCustomerIsCreatedWithInvalidEmail() {
        var customerId = CustomerId.from(1L);
        var email = "invalid-email";
        var name = "Teste";

        assertThatThrownBy(() -> Customer.from(customerId, email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageStartingWith("Email is invalid");
    }

    @Test
    void shouldThrowErrorWhenCustomerIsCreatedWithNullName() {
        var customerId = CustomerId.from(1L);
        var email = "test@email.com";
        String name = null;

        assertThatThrownBy(() -> Customer.from(customerId, email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Name should not be blank");
    }

    @Test
    void shouldThrowErrorWhenCustomerIsCreatedWithEmptyName() {
        var customerId = CustomerId.from(1L);
        var email = "test@email.com";
        var name = "";

        assertThatThrownBy(() -> Customer.from(customerId, email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Name should not be blank");
    }

    @Test
    void shouldChangeEmailWhenCustomerIsUpdated() {
        var john = john();
        var newEmail = CustomerEmail.from("newjohn@email.com");
        var name = john.getName();

        john.update(newEmail, name);

        assertThat(john.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void shouldChangeNameWhenCustomerIsUpdated() {
        var john = john();
        var email = john.getEmail();
        var newName = CustomerName.from("New John");

        john.update(email, newName);

        assertThat(john.getName()).isEqualTo(newName);
    }

    @Test
    void shouldThrowErrorWhenCustomerIsUpdatedWithNullEmail() {
        var john = john();
        CustomerEmail email = null;
        var name = john.getName();

        assertThatThrownBy(() -> john.update(email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Email should not be null");
    }

    @Test
    void shouldThrowErrorWhenCustomerIsUpdatedWithNullName() {
        var john = john();
        var email = john.getEmail();
        CustomerName name = null;

        assertThatThrownBy(() -> john.update(email, name))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Name should not be null");
    }

}
