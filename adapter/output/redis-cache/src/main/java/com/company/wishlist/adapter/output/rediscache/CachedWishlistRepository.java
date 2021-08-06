package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.entity.CachedWishlistEntity;
import com.company.wishlist.adapter.output.rediscache.mapper.CachedWishlistMapper;
import com.company.wishlist.adapter.output.rediscache.repository.WishlistRedisRepository;
import com.company.wishlist.core.customer.Customer;
import com.company.wishlist.core.repository.WishlistRepository;
import com.company.wishlist.core.wishlist.Wishlist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@Qualifier("cached")
@Slf4j
public class CachedWishlistRepository implements WishlistRepository {

    private final WishlistRepository wishlistRepository;

    private final CachedWishlistMapper cachedWishlistMapper;

    private final WishlistRedisRepository wishlistRedisRepository;

    @Autowired
    CachedWishlistRepository(
            final WishlistRepository wishlistRepository,
            final CachedWishlistMapper cachedWishlistMapper,
            final WishlistRedisRepository wishlistRedisRepository) {
        this.wishlistRepository = wishlistRepository;
        this.cachedWishlistMapper = cachedWishlistMapper;
        this.wishlistRedisRepository = wishlistRedisRepository;
    }

    @Override
    public void save(final Wishlist wishList) {
        log.debug("Deleting wishlist in cached repository: " + wishList.customer().id());

        wishlistRepository.save(wishList);
        wishlistRedisRepository.deleteById(wishList.customer().id());
    }

    @Override
    public Wishlist getBy(final Customer customer) {
        log.debug("Finding wishlist in cached repository: " + customer.id());

        var cachedWishlistEntity = wishlistRedisRepository.findById(customer.id())
                .orElseGet(fromRepositoryAndSaveInCache(customer));

        return cachedWishlistMapper.toDomain(cachedWishlistEntity);
    }

    private Supplier<CachedWishlistEntity> fromRepositoryAndSaveInCache(final Customer customer) {
        return () -> {
            log.info("Wishlist not found in cached repository: " + customer.id());

            var wishlist = wishlistRepository.getBy(customer);
            var cachedWishlistEntity = cachedWishlistMapper.toEntity(wishlist);

            wishlistRedisRepository.save(cachedWishlistEntity);

            return cachedWishlistEntity;
        };
    }
}
