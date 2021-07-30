package com.company.wishlist.adapter.input.springrestcontroller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@Schema(title = "Wishlist")
public class WishlistDTO {
    private List<ProductDTO> products;
}
