package br.com.company.wishlist.adapter.input.mapper;

import br.com.company.wishlist.core.domain.Customer;
import br.com.company.wishlist.adapter.input.dto.CreatedCustomerDTO;
import br.com.company.wishlist.adapter.input.dto.IncomingCustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerInputMapper {

    public Customer toDomain(IncomingCustomerDTO incomingCustomerDTO) {
        return Customer.create(
                incomingCustomerDTO.getEmail(),
                incomingCustomerDTO.getName()
        );
    }

    public CreatedCustomerDTO toDTO(Customer customer) {
        return CreatedCustomerDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .build();
    }
}
