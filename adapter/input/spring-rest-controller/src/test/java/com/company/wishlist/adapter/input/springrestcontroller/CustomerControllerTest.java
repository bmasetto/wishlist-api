package com.company.wishlist.adapter.input.springrestcontroller;

import com.company.wishlist.adapter.input.springrestcontroller.mapper.CustomerInputMapper;
import com.company.wishlist.core.customer.CreateCustomer;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.DeleteCustomer;
import com.company.wishlist.core.customer.GetCustomer;
import com.company.wishlist.core.customer.UpdateCustomer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.company.wishlist.adapter.input.springrestcontroller.InputTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CreateCustomer createCustomer;

    @Mock
    private GetCustomer getCustomer;

    @Mock
    private UpdateCustomer updateCustomer;

    @Mock
    private DeleteCustomer deleteCustomer;


    @Spy
    private CustomerInputMapper customerInputMapper;

    @Test
    void shouldReturnCustomerWhenPostingCustomer() {
        when(createCustomer.create(any(Customer.class)))
                .thenReturn(john());

        var response = customerController.postCustomer(incomingJohnDTO());

        assertThat(response.getBody()).isEqualTo(johnDTO());
    }

    @Test
    void shouldReturnStatusCodeWhenPostingCustomer() {
        when(createCustomer.create(any(Customer.class)))
                .thenReturn(john());

        var response = customerController.postCustomer(incomingJohnDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnCustomerWhenGettingCustomerById() {
        when(getCustomer.getBy(johnId()))
                .thenReturn(john());

        var response = customerController.getCustomer(john().id());

        assertThat(response.getBody()).isEqualTo(johnDTO());
    }

    @Test
    void shouldReturnStatusCodeWhenGettingCustomerById() {
        when(getCustomer.getBy(johnId()))
                .thenReturn(john());

        var response = customerController.getCustomer(john().id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnCustomerWhenPuttingCustomer() {
        when(updateCustomer.update(john()))
                .thenReturn(john());

        var response = customerController.putCustomer(john().id(), incomingJohnDTO());

        assertThat(response.getBody()).isEqualTo(johnDTO());
    }

    @Test
    void shouldReturnStatusCodeWhenPuttingCustomer() {
        when(updateCustomer.update(john()))
                .thenReturn(john());

        var response = customerController.putCustomer(john().id(), incomingJohnDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnNothingWhenDeletingCustomer() {
        doNothing().when(deleteCustomer).delete(johnId());

        var response = customerController.deleteCustomer(john().id());

        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldReturnStatusCodeWhenDeletingCustomer() {
        doNothing().when(deleteCustomer).delete(johnId());

        var response = customerController.deleteCustomer(john().id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
