package com.company.wishlist.adapter.input.springrestcontroller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Schema(title = "IncomingProduct")
public class IncomingProductDTO {

    @NotBlank
    @Schema(example = "1bf0f365-fbdd-4e21-9786-da459d78dd1f")
    private String id;

}
