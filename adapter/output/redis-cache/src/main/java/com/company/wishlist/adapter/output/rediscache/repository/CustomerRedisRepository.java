package com.company.wishlist.adapter.output.rediscache.repository;

import com.company.wishlist.adapter.output.rediscache.entity.CachedCustomerEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRedisRepository extends CrudRepository<CachedCustomerEntity, Long> {
}
