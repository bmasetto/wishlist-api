package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.NotFoundException;
import com.company.wishlist.core.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.core.customer.UseCaseTestResources.john;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerTest {

    @InjectMocks
    private DeleteCustomer deleteCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldDeleteCustomer() {
        var john = john();

        when(customerRepository.findBy(john.getId()))
                .thenReturn(Optional.of(john));

        deleteCustomer.delete(john.getId());

        verify(customerRepository, times(1)).delete(john());
    }

    @Test
    void shouldThrowErrorWhenCustomerDoesNotExist() {
        var john = john();

        when(customerRepository.findBy(john.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> deleteCustomer.delete(john.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Customer not found");
    }

}
