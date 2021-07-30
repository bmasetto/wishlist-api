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
@Schema(title = "IncomingCustomer")
public class IncomingCustomerDTO {

    @NotBlank
    @Schema(example = "Bruno")
    private String name;

    @NotBlank
    @Schema(example = "brunomasetto@email.com")
    private String email;
}
