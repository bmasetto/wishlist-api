package com.company.wishlist.adapter.output.rediscache.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@RedisHash(value = "Customer", timeToLive = 3600)
public class CachedCustomerEntity {

    @Id
    private Long id;
    private String name;
    private String email;

}
