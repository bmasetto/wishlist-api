package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.mapper.CachedCustomerMapper;
import com.company.wishlist.adapter.output.rediscache.repository.CustomerRedisRepository;
import com.company.wishlist.core.repository.CustomerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.adapter.output.rediscache.RedisOutputTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CachedCustomerRepositoryTest {

    @InjectMocks
    private CachedCustomerRepository cachedCustomerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Spy
    private CachedCustomerMapper cachedCustomerMapper;

    @Mock
    private CustomerRedisRepository customerRedisRepository;

    @Test
    void shouldThrowErrorWhenCreatingUser() {
        assertThatThrownBy(() -> cachedCustomerRepository.create(john()))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("Is is not possible to created customer by cached class");

        verify(customerRedisRepository, never()).findById(johnId().value());
        verify(customerRedisRepository, never()).save(johnEntity());
        verify(customerRepository, never()).create(john());
    }

    @Test
    void shouldNotManageCachingWhenFindingCustomerByIdAndCustomerExistsInCache() {
        when(customerRedisRepository.findById(johnId().value()))
                .thenReturn(Optional.of(johnEntity()));

        var customer = cachedCustomerRepository.findBy(johnId());

        assertThat(customer).isEqualTo(Optional.of(john()));

        verify(customerRedisRepository, times(1)).findById(johnId().value());
        verify(customerRedisRepository, never()).save(johnEntity());
        verify(customerRepository, never()).findBy(johnId());
    }

    @Test
    void shouldManageCachingWhenFindingCustomerByIdAndCustomerDoesNotExistInCache() {
        when(customerRedisRepository.findById(johnId().value()))
                .thenReturn(Optional.empty());
        when(customerRepository.findBy(johnId()))
                .thenReturn(Optional.of(john()));

        var customer = cachedCustomerRepository.findBy(johnId());

        assertThat(customer).isEqualTo(Optional.of(john()));

        verify(customerRedisRepository, times(1)).findById(johnId().value());
        verify(customerRedisRepository, times(1)).save(johnEntity());
        verify(customerRepository, times(1)).findBy(johnId());
    }

    @Test
    void shouldNotUseCachingWhenFindingCustomerByEmail() {
        when(customerRepository.findByEmail(john().email()))
                .thenReturn(Optional.of(john()));

        var customer = cachedCustomerRepository.findByEmail(john().email());

        assertThat(customer).isEqualTo(Optional.of(john()));

        verify(customerRepository, times(1)).findByEmail(john().email());
    }

    @Test
    void shouldNotUseCachingWhenFindingCustomerByEmailAndDifferentId() {
        when(customerRepository.findByEmailAndDifferentId(john().email(), johnId()))
                .thenReturn(Optional.of(john()));

        var customer = cachedCustomerRepository.findByEmailAndDifferentId(john().email(), johnId());

        assertThat(customer).isEqualTo(Optional.of(john()));

        verify(customerRepository, times(1)).findByEmailAndDifferentId(john().email(), johnId());
    }

    @Test
    void shouldManageCachingWhenUpdatingCustomer() {
        doNothing().when(customerRedisRepository).deleteById(johnId().value());
        doNothing().when(customerRepository).update(john());

        cachedCustomerRepository.update(john());

        verify(customerRedisRepository, times(1)).deleteById(johnId().value());
        verify(customerRepository, times(1)).update(john());
    }

    @Test
    void shouldManageCachingWhenDeletingCustomer() {
        doNothing().when(customerRedisRepository).deleteById(johnId().value());
        doNothing().when(customerRepository).delete(john());

        cachedCustomerRepository.delete(john());

        verify(customerRedisRepository, times(1)).deleteById(johnId().value());
        verify(customerRepository, times(1)).delete(john());
    }

}
