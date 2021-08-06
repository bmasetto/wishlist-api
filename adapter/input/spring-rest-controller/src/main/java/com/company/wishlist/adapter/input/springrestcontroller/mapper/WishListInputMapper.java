package com.company.wishlist.adapter.input.springrestcontroller.mapper;

import com.company.wishlist.adapter.input.springrestcontroller.dto.WishlistDTO;
import com.company.wishlist.core.wishlist.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListInputMapper {

    private final ProductInputMapper productInputMapper;

    @Autowired
    public WishListInputMapper(final ProductInputMapper productInputMapper) {
        this.productInputMapper = productInputMapper;
    }

    public WishlistDTO toDTO(final Wishlist wishList) {
        return WishlistDTO.builder()
                .products(productInputMapper.toDTO(wishList.products()))
                .build();
    }
}
