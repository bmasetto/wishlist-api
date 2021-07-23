package br.com.company.wishlist.adapter.input.controller;

import br.com.company.wishlist.core.usecase.CreateCustomer;
import br.com.company.wishlist.adapter.input.dto.CreatedCustomerDTO;
import br.com.company.wishlist.adapter.input.dto.IncomingCustomerDTO;
import br.com.company.wishlist.adapter.input.mapper.CustomerInputMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CustomerController {

    private final CustomerInputMapper customerInputMapper;

    private final CreateCustomer createCustomer;

    @Autowired
    public CustomerController(CustomerInputMapper customerInputMapper, CreateCustomer createCustomer) {
        this.customerInputMapper = customerInputMapper;
        this.createCustomer = createCustomer;
    }

    @PostMapping(value = "/customer")
    public ResponseEntity<CreatedCustomerDTO> postCustomer(@RequestBody IncomingCustomerDTO incomingCustomerDTO) {

        var incomingCustomer = customerInputMapper.toDomain(incomingCustomerDTO);
        var createdCustomer = createCustomer.create(incomingCustomer);
        var createdCustomerDTO = customerInputMapper.toDTO(createdCustomer);

        var location = URI.create("/customer/" + createdCustomer.getId());

        return ResponseEntity.created(location)
                .body(createdCustomerDTO);
    }
}
