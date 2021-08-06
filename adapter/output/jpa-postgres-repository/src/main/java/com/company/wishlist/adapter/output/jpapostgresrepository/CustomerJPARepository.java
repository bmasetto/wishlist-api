package com.company.wishlist.adapter.output.jpapostgresrepository;

import com.company.wishlist.adapter.output.jpapostgresrepository.entity.CustomerEntity;
import com.company.wishlist.adapter.output.jpapostgresrepository.mapper.CustomerOutputMapper;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@Primary
@Slf4j
public class CustomerJPARepository implements CustomerRepository {

    private final EntityManager entityManager;

    private final CustomerOutputMapper customerOutputMapper;

    @Autowired
    CustomerJPARepository(final EntityManager entityManager, final CustomerOutputMapper customerOutputMapper) {
        this.entityManager = entityManager;
        this.customerOutputMapper = customerOutputMapper;
    }

    @Override
    @Transactional
    public Customer create(final Customer customer) {
        log.debug("Saving customer");

        var customerEntity = customerOutputMapper.toEntity(customer);
        entityManager.persist(customerEntity);

        return customerOutputMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findBy(final CustomerId customerId) {
        log.debug("Finding customer: " + customerId.value());

        var entity = entityManager.find(CustomerEntity.class, customerId.value());

        return Optional.ofNullable(entity)
                .map(customerOutputMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        log.debug("Finding customer by email: " + email);

        var customerEntity = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.email = :email", CustomerEntity.class)
                .setParameter("email", email)
                .getResultStream()
                .findAny();

        return customerEntity.map(customerOutputMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmailAndDifferentId(final String email, final CustomerId id) {
        log.debug("Finding customer by email and different id: " + email + id.value());

        var customerEntity = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.email = :email AND id != :id", CustomerEntity.class)
                .setParameter("email", email)
                .setParameter("id", id.value())
                .getResultStream()
                .findAny();

        return customerEntity.map(customerOutputMapper::toDomain);
    }

    @Override
    @Transactional
    public void update(final Customer customer) {
        log.debug("Updating customer: " + customer.id());

        var customerEntity = entityManager.find(CustomerEntity.class, customer.id());

        customerEntity.setEmail(customer.email());
        customerEntity.setName(customer.name());

        entityManager.merge(customerEntity);
    }

    @Override
    @Transactional
    public void delete(final Customer customer) {
        log.debug("Deleting customer: " + customer.id());

        var entity = entityManager.find(CustomerEntity.class, customer.id());

        entityManager.remove(entity);
    }

}
