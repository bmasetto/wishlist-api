package com.company.wishlist.adapter.input.springrestcontroller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
@Schema(title = "Error")
public class ErrorDTO {

    @Schema(example = "Status error")
    private HttpStatus status;

    @Schema(example = "Detailed error")
    private String message;
}
