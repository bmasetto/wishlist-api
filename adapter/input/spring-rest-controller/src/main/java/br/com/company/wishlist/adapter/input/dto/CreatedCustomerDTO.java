package br.com.company.wishlist.adapter.input.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatedCustomerDTO {
    private String id;
    private String name;
    private String email;
}
