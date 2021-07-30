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
@RedisHash(value = "Product", timeToLive = 3600)
public class CachedProductEntity {

    @Id
    private String id;
    private String title;
    private String image;
    private Double price;
    private Double reviewScore;

}
