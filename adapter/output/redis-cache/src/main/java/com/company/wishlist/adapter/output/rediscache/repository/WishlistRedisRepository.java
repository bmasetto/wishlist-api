package com.company.wishlist.adapter.output.rediscache.repository;

import com.company.wishlist.adapter.output.rediscache.entity.CachedWishlistEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRedisRepository extends CrudRepository<CachedWishlistEntity, Long> {
}
