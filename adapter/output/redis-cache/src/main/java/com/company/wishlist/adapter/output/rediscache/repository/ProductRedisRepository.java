package com.company.wishlist.adapter.output.rediscache.repository;

import com.company.wishlist.adapter.output.rediscache.entity.CachedProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRedisRepository extends CrudRepository<CachedProductEntity, String> {
}
