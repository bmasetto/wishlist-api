package com.company.wishlist.adapter.input.springrestcontroller.mapper;

import com.company.wishlist.adapter.input.springrestcontroller.dto.IncomingProductDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.ProductDTO;
import com.company.wishlist.core.product.Product;
import com.company.wishlist.core.product.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class ProductInputMapper {

    public List<ProductId> toDomain(final List<IncomingProductDTO> productsToBeDeleted) {
        return productsToBeDeleted.stream()
                .map(productToBeDeleted -> ProductId.from(productToBeDeleted.getId()))
                .collect(toList());
    }

    public List<ProductDTO> toDTO(final Set<Product> products) {
        return products.stream()
                .map(product -> ProductDTO.builder()
                        .id(product.id().value())
                        .title(product.title())
                        .image(product.image())
                        .price(product.price())
                        .reviewScore(product.reviewScore())
                        .build())
                .collect(toList());
    }
}
