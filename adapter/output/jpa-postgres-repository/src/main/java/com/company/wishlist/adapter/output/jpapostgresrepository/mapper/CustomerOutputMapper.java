package com.company.wishlist.adapter.output.jpapostgresrepository.mapper;

import com.company.wishlist.adapter.output.jpapostgresrepository.entity.CustomerEntity;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;

import org.springframework.stereotype.Component;

@Component
public class CustomerOutputMapper {

    public Customer toDomain(final CustomerEntity entity) {
        return Customer.from(
                CustomerId.from(entity.getId()),
                entity.getEmail(),
                entity.getName()
        );
    }

    public CustomerEntity toEntity(final Customer customer) {
        return CustomerEntity.builder()
                .email(customer.email())
                .name(customer.name())
                .build();
    }
}
