package com.company.wishlist.core.customer;

import com.company.wishlist.core.exception.InvalidDataException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerTest {

    @InjectMocks
    private CreateCustomer createCustomer;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateCustomer() {
        var mockedCreatedCustomer = mock(Customer.class);

        when(customerRepository.create(john()))
                .thenReturn(mockedCreatedCustomer);

        var createdCustomer = createCustomer.create(john());

        verify(customerRepository, times(1)).create(john());
        assertThat(createdCustomer).isEqualTo(mockedCreatedCustomer);
    }

    @Test
    void shouldThrowErrorWhenCustomerEmailAlreadyExists() {
        var mockedDuplicatedCustomer = mock(Customer.class);

        when(customerRepository.findByEmail(john().email()))
                .thenReturn(Optional.ofNullable(mockedDuplicatedCustomer));

        assertThatThrownBy(() -> createCustomer.create(john()))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageStartingWith("Email already exists");
    }

}
