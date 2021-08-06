package com.company.wishlist.adapter.output.rediscache.mapper;

import com.company.wishlist.adapter.output.rediscache.entity.CachedCustomerEntity;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;

import org.springframework.stereotype.Component;

@Component
public class CachedCustomerMapper {

    public Customer toDomain(final CachedCustomerEntity cachedCustomerEntity) {
        return Customer.from(
                CustomerId.from(cachedCustomerEntity.getId()),
                cachedCustomerEntity.getEmail(),
                cachedCustomerEntity.getName()
        );
    }

    public CachedCustomerEntity toEntity(final Customer customer) {
        return CachedCustomerEntity.builder()
                .id(customer.id())
                .id(customer.id())
                .name(customer.name())
                .email(customer.email())
                .build();
    }
}
