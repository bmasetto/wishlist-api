package com.company.wishlist.adapter.output.rediscache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@RedisHash(value = "Wishlist", timeToLive = 3600)
public class CachedWishlistEntity {

    @Id
    private Long customerId;

    private CachedCustomerEntity customer;

    private Set<CachedProductEntity> products = Collections.emptySet();

}

