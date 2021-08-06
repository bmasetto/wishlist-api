package com.company.wishlist.adapter.output.productrestclient;

import com.company.wishlist.adapter.output.productrestclient.handler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductRestClientConfig {

    private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Autowired
    public ProductRestClientConfig(final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        this.restTemplateResponseErrorHandler = restTemplateResponseErrorHandler;
    }

    @Bean
    RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.errorHandler(restTemplateResponseErrorHandler).build();
    }
}
