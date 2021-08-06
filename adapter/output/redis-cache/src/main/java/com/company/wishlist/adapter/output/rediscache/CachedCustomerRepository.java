package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.entity.CachedCustomerEntity;
import com.company.wishlist.adapter.output.rediscache.mapper.CachedCustomerMapper;
import com.company.wishlist.adapter.output.rediscache.repository.CustomerRedisRepository;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@Qualifier("cached")
@Slf4j
public class CachedCustomerRepository implements CustomerRepository {

    private final CustomerRepository customerRepository;

    private final CachedCustomerMapper cachedCustomerMapper;

    private final CustomerRedisRepository customerRedisRepository;

    @Autowired
    CachedCustomerRepository(final CustomerRepository customerRepository,
                             final CachedCustomerMapper cachedCustomerMapper,
                             final CustomerRedisRepository customerRedisRepository) {
        this.customerRepository = customerRepository;
        this.cachedCustomerMapper = cachedCustomerMapper;
        this.customerRedisRepository = customerRedisRepository;
    }

    @Override
    public Customer create(final Customer customer) {
        throw new UnsupportedOperationException("Is is not possible to created customer by cached class");
    }

    @Override
    public Optional<Customer> findBy(final CustomerId customerId) {
        log.debug("Finding customer in cached repository: " + customerId.value());

        var cachedCustomerEntity = customerRedisRepository.findById(customerId.value())
                .orElseGet(fromRepositoryAndSaveInCache(customerId));

        return Optional.ofNullable(cachedCustomerEntity).map(cachedCustomerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(final String email) {
        return customerRepository.findByEmail(email); //Never cached
    }

    @Override
    public Optional<Customer> findByEmailAndDifferentId(final String email, final CustomerId id) {
        return customerRepository.findByEmailAndDifferentId(email, id); //Never cached
    }

    @Override
    public void update(final Customer customer) {
        log.debug("Deleting customer in cached repository: " + customer.id());

        customerRepository.update(customer);

        customerRedisRepository.deleteById(customer.id());
    }

    @Override
    public void delete(final Customer customer) {
        log.debug("Deleting customer in cached repository: " + customer.id());

        customerRepository.delete(customer);

        customerRedisRepository.deleteById(customer.id());
    }

    private Supplier<CachedCustomerEntity> fromRepositoryAndSaveInCache(final CustomerId customerId) {
        return () -> {
            log.info("Customer not found in cached repository: " + customerId.value());

            var cachedCustomerEntity = customerRepository.findBy(customerId)
                    .map(cachedCustomerMapper::toEntity);

            cachedCustomerEntity.ifPresent(customerRedisRepository::save);

            return cachedCustomerEntity.orElse(null);
        };
    }
}
