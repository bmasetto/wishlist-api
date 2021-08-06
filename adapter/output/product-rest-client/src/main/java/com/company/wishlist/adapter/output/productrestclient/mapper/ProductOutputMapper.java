package com.company.wishlist.adapter.output.productrestclient.mapper;

import com.company.wishlist.adapter.output.productrestclient.dto.ProductDTO;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ProductOutputMapper {

    public Product toDomain(final ProductDTO productDTO) {
        return Product.from(
                ProductId.from(productDTO.getId()),
                productDTO.getTitle(),
                productDTO.getImage(),
                productDTO.getPrice(),
                productDTO.getReviewScore()
        );
    }

}
