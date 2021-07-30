package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.core.customer.UseCaseTestResources.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerTest {

    @InjectMocks
    private UpdateCustomer updateCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldUpdateCustomer() {
        var incomingJohn = Customer.from(
                john().getId(),
                "newjhown@email.com",
                "New John"
        );
        var john = mock(Customer.class);

        when(customerRepository.findBy(incomingJohn.getId()))
                .thenReturn(Optional.of(john));

        when(customerRepository.findByEmailAndDifferentId(incomingJohn.email(), incomingJohn.getId()))
                .thenReturn(Optional.empty());

        updateCustomer.update(incomingJohn);

        verify(customerRepository, times(1)).update(john);
        verify(john, times(1)).update(incomingJohn.getEmail(), incomingJohn.getName());
    }

    @Test
    void shouldThrowErrorWhenCustomerDoesNotExistis() {
        var incomingJohn = Customer.from(
                CustomerId.from(999L),
                "newjhown@email.com",
                "New John"
        );

        when(customerRepository.findBy(incomingJohn.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> updateCustomer.update(incomingJohn))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");
    }

    @Test
    void shouldThrowErrorWhenCustomerEmailAlreadyExists() {
        var incomingJohn = Customer.from(
                CustomerId.from(999L),
                "jhown@email.com",
                "Other John"
        );

        when(customerRepository.findBy(incomingJohn.getId()))
                .thenReturn(Optional.of(incomingJohn));

        when(customerRepository.findByEmailAndDifferentId(incomingJohn.email(), incomingJohn.getId()))
                .thenReturn(Optional.of(john()));

        assertThatThrownBy(() -> updateCustomer.update(incomingJohn))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageStartingWith("Email already exists");
    }

}
