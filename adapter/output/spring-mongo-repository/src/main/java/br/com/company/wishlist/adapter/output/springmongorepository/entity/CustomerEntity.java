package br.com.company.wishlist.adapter.output.springmongorepository.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "customers")
public class CustomerEntity {

    @Id
    private String id;

    @Indexed(unique=true)
    private String email;

    private String name;
}
