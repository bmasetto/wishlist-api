package com.company.wishlist.adapter.input.springrestcontroller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(title = "Customer")
public class CustomerDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Bruno")
    private String name;

    @Schema(example = "brunomasetto@email.com")
    private String email;
}
