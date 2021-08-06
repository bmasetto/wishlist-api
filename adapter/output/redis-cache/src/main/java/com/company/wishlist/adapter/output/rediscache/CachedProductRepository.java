package com.company.wishlist.adapter.output.rediscache;

import com.company.wishlist.adapter.output.rediscache.entity.CachedProductEntity;
import com.company.wishlist.adapter.output.rediscache.mapper.CachedProductMapper;
import com.company.wishlist.adapter.output.rediscache.repository.ProductRedisRepository;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@Qualifier("cached")
@Slf4j
public class CachedProductRepository implements ProductRepository {

    private final ProductRepository productRepository;

    private final CachedProductMapper cachedProductMapper;

    private final ProductRedisRepository productRedisRepository;

    @Autowired
    CachedProductRepository(
            final ProductRepository productRepository,
            final CachedProductMapper cachedProductMapper,
            final ProductRedisRepository productRedisRepository) {
        this.productRepository = productRepository;
        this.cachedProductMapper = cachedProductMapper;
        this.productRedisRepository = productRedisRepository;
    }

    @Override
    public Optional<Product> findBy(final ProductId productId) {
        log.debug("Finding product in cached repository: " + productId.value());

        var cachedProductEntity = productRedisRepository.findById(productId.value())
                .orElseGet(fromRepositoryAndSaveInCache(productId));

        return Optional.ofNullable(cachedProductEntity).map(cachedProductMapper::toDomain);
    }

    private Supplier<CachedProductEntity> fromRepositoryAndSaveInCache(final ProductId productId) {
        return () -> {
            log.info("Product not found in cached repository: " + productId.value());

            var cachedProductEntity = productRepository.findBy(productId)
                    .map(product -> cachedProductMapper.toEntity(productId, product));

            cachedProductEntity.ifPresent(productRedisRepository::save);

            return cachedProductEntity.orElse(null);
        };
    }

}
