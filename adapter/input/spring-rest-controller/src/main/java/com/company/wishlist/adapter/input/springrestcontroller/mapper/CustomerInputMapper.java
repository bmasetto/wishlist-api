package com.company.wishlist.adapter.input.springrestcontroller.mapper;

import com.company.wishlist.adapter.input.springrestcontroller.dto.CustomerDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.IncomingCustomerDTO;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerInputMapper {

    public Customer toDomain(final IncomingCustomerDTO incomingCustomerDTO) {
        return toDomain(-1L, incomingCustomerDTO);
    }

    public Customer toDomain(final Long id, final IncomingCustomerDTO incomingCustomerDTO) {
        return Customer.from(
                CustomerId.from(id),
                incomingCustomerDTO.getEmail(),
                incomingCustomerDTO.getName()
        );
    }

    public CustomerDTO toDTO(final Customer customer) {
        return CustomerDTO.builder()
                .id(customer.id())
                .email(customer.email())
                .name(customer.name())
                .build();
    }
}
