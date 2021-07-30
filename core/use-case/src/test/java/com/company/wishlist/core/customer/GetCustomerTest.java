package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.core.customer.UseCaseTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomerTest {

    @InjectMocks
    private GetCustomer getCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldReturnCustomer() {
        var customerId = CustomerId.from(1L);

        when(customerRepository.findBy(customerId))
                .thenReturn(Optional.of(john()));

        Customer customer = getCustomer.getBy(customerId);

        assertThat(customer.getId()).isEqualTo(customerId);
    }

    @Test
    void shouldThrowErrorWhenCustomerIsNotFound() {
        var customerId = CustomerId.from(1L);

        when(customerRepository.findBy(customerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCustomer.getBy(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");
    }

}