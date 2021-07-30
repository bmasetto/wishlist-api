package com.company.wishlist.adapter.output.productrestclient;

import com.company.wishlist.adapter.output.productrestclient.dto.ProductDTO;
import com.company.wishlist.adapter.output.productrestclient.mapper.ProductOutputMapper;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import com.company.wishlist.core.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@Primary
@Slf4j
public class ProductRestClient implements ProductRepository {

    private final RestTemplate restTemplate;

    private final ProductOutputMapper productOutputMapper;

    @Value("${product-api.url}")
    private String fooResourceUrl;

    @Autowired
    ProductRestClient(RestTemplate restTemplate, ProductOutputMapper productOutputMapper) {
        this.restTemplate = restTemplate;
        this.productOutputMapper = productOutputMapper;
    }

    @Override
    public Optional<Product> findBy(ProductId productId) {
        log.debug("Finding product: " + productId.value());

        var productDTO = restTemplate
                .getForEntity(fooResourceUrl + "/product/" + productId.value() + "/", ProductDTO.class)
                .getBody();

        if (productDTO == null || productDTO.getId() == null) {
            return Optional.empty();
        }

        return Optional.of(productDTO).map(productOutputMapper::toDomain);
    }

}
