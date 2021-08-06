package com.company.wishlist.adapter.output.jpapostgresrepository.mapper;

import org.junit.jupiter.api.Test;

import static com.company.wishlist.adapter.output.jpapostgresrepository.JPAOutputTestResources.john;
import static com.company.wishlist.adapter.output.jpapostgresrepository.JPAOutputTestResources.johnEntity;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerOutputMapperTest {

    @Test
    void shouldMapIdWhenConvertedToDomain() {
        var customer = new CustomerOutputMapper().toDomain(johnEntity());
        assertThat(customer.id()).isEqualTo(johnEntity().getId());
    }

    @Test
    void shouldMapEmailWhenConvertedToDomain() {
        var customer = new CustomerOutputMapper().toDomain(johnEntity());
        assertThat(customer.email()).isEqualTo(johnEntity().getEmail());
    }

    @Test
    void shouldMapNameWhenConvertedToDomain() {
        var customer = new CustomerOutputMapper().toDomain(johnEntity());
        assertThat(customer.name()).isEqualTo(johnEntity().getName());
    }

    @Test
    void shouldMapIdWhenConvertedToEntity() {
        var customerEntity = new CustomerOutputMapper().toEntity(john());
        assertThat(customerEntity.getId()).isNull();
    }

    @Test
    void shouldMapEmailWhenConvertedToEntity() {
        var customerEntity = new CustomerOutputMapper().toEntity(john());
        assertThat(customerEntity.getEmail()).isEqualTo(john().email());
    }

    @Test
    void shouldMapNameWhenConvertedToEntity() {
        var customerEntity = new CustomerOutputMapper().toEntity(john());
        assertThat(customerEntity.getName()).isEqualTo(john().name());
    }

}
