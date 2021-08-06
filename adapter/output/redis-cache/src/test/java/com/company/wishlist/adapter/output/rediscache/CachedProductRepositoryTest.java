package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.mapper.CachedProductMapper;
import com.company.wishlist.adapter.output.rediscache.repository.ProductRedisRepository;
import com.company.wishlist.core.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.company.wishlist.adapter.output.rediscache.RedisOutputTestResources.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CachedProductRepositoryTest {

    @InjectMocks
    private CachedProductRepository cachedProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private CachedProductMapper cachedProductMapper;

    @Mock
    private ProductRedisRepository productRedisRepository;

    @Test
    void shouldNotManageCachingWhenFindingProductByIdAndProductExistsInCache() {
        when(productRedisRepository.findById(tvId().value()))
                .thenReturn(Optional.of(tvEntity()));

        var product = cachedProductRepository.findBy(tvId());

        assertThat(product).isEqualTo(Optional.of(tv()));

        verify(productRedisRepository, times(1)).findById(tvId().value());
        verify(productRedisRepository, never()).save(tvEntity());
        verify(productRepository, never()).findBy(tvId());
    }

    @Test
    void shouldManageCachingWhenFindingProductByIdAndProductDoesNotExistInCache() {
        when(productRedisRepository.findById(tvId().value()))
                .thenReturn(Optional.empty());
        when(productRepository.findBy(tvId()))
                .thenReturn(Optional.of(tv()));

        var product = cachedProductRepository.findBy(tvId());

        assertThat(product).isEqualTo(Optional.of(tv()));

        verify(productRedisRepository, times(1)).findById(tvId().value());
        verify(productRedisRepository, times(1)).save(tvEntity());
        verify(productRepository, times(1)).findBy(tvId());
    }

}
