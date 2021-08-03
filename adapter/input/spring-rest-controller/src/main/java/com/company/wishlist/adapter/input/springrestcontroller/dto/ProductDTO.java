package com.company.wishlist.adapter.input.springrestcontroller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(title = "Product")
public class ProductDTO {

    @Schema(example = "1bf0f365-fbdd-4e21-9786-da459d78dd1f")
    private String id;

    @Schema(example = "TV")
    private String title;

    @Schema(example = "http://company.com/images/1bf0f365-fbdd-4e21-9786-da459d78dd1f.jpg")
    private String image;

    @Schema(example = "1699.0")
    private Double price;

    @Schema
    private Double reviewScore;
}
