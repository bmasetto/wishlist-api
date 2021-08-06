package com.company.wishlist.adapter.output.productrestclient;

import com.company.wishlist.adapter.output.productrestclient.dto.ProductDTO;

public class RestClientOutputTestResources {

    public static ProductDTO tvDTO() {
        return ProductDTO.builder()
                .id("123")
                .title("TV")
                .image("tv.jpg")
                .price(10.0)
                .reviewScore(1.0)
                .build();
    }

}
