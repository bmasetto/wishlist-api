package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.mapper.CachedCustomerMapper;
import com.company.wishlist.adapter.output.rediscache.mapper.CachedProductMapper;
import com.company.wishlist.adapter.output.rediscache.mapper.CachedWishlistMapper;
import com.company.wishlist.adapter.output.rediscache.repository.WishlistRedisRepository;
import com.company.wishlist.core.repository.WishlistRepository;
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
class CachedWishlistRepositoryTest {

    @InjectMocks
    private CachedWishlistRepository cachedWishlistRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @Spy
    private CachedWishlistMapper cachedWishlistMapper = new CachedWishlistMapper(
            new CachedProductMapper(),
            new CachedCustomerMapper()
    );

    @Mock
    private WishlistRedisRepository wishlistRedisRepository;

    @Test
    void shouldManageCachingWhenSavingWishlist() {
        doNothing().when(wishlistRedisRepository).deleteById(johnId().value());
        doNothing().when(wishlistRepository).save(johnWishlist());

        cachedWishlistRepository.save(johnWishlist());

        verify(wishlistRedisRepository, times(1)).deleteById(johnId().value());
        verify(wishlistRepository, times(1)).save(johnWishlist());
    }

    @Test
    void shouldNotManageCachingWhenFindingWishlistAndCustomerExistsInCache() {
        when(wishlistRedisRepository.findById(johnId().value()))
                .thenReturn(Optional.of(johnWishlistEntity()));

        var wishlist = cachedWishlistRepository.getBy(john());

        assertThat(wishlist).isEqualTo(johnWishlist());

        verify(wishlistRedisRepository, times(1)).findById(johnId().value());
        verify(wishlistRedisRepository, never()).save(johnWishlistEntity());
        verify(wishlistRepository, never()).getBy(john());
    }

    @Test
    void shouldManageCachingWhenFindingWishlistAndCustomerExistsInCache() {
        when(wishlistRedisRepository.findById(johnId().value()))
                .thenReturn(Optional.empty());
        when(wishlistRepository.getBy(john()))
                .thenReturn(johnWishlist());

        var wishlist = cachedWishlistRepository.getBy(john());

        assertThat(wishlist).isEqualTo(johnWishlist());

        verify(wishlistRedisRepository, times(1)).findById(johnId().value());
        verify(wishlistRedisRepository, times(1)).save(johnWishlistEntity());
        verify(wishlistRepository, times(1)).getBy(john());
    }

}
