package br.com.company.wishlist.adapter.output.springmongorepository.mapper;

import br.com.company.wishlist.core.domain.Customer;
import br.com.company.wishlist.adapter.output.springmongorepository.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerOutputMapper {

    public CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .build();
    }

    public Customer toDomain(CustomerEntity entity) {
        return Customer.create(
                entity.getEmail(),
                entity.getName()
        );
    }
}
