package com.company.wishlist.adapter.input.springrestcontroller;

import com.company.wishlist.adapter.input.springrestcontroller.dto.CustomerDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.ErrorDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.IncomingCustomerDTO;
import com.company.wishlist.adapter.input.springrestcontroller.mapper.CustomerInputMapper;
import com.company.wishlist.core.customer.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("customer")
@Slf4j
@Tag(name = "Customer")
public class CustomerController {

    private final CreateCustomer createCustomer;
    private final GetCustomer getCustomer;
    private final UpdateCustomer updateCustomer;
    private final DeleteCustomer deleteCustomer;

    private final CustomerInputMapper customerInputMapper;

    @Autowired
    CustomerController(CustomerInputMapper customerInputMapper, CreateCustomer createCustomer,
                       GetCustomer getCustomer, UpdateCustomer updateCustomer, DeleteCustomer deleteCustomer) {
        this.customerInputMapper = customerInputMapper;
        this.createCustomer = createCustomer;
        this.getCustomer = getCustomer;
        this.updateCustomer = updateCustomer;
        this.deleteCustomer = deleteCustomer;
    }

    @Operation(summary = "Create customer", responses = {
            @ApiResponse(description = "Created customer", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(description = "Invalid customer error", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> postCustomer(@RequestBody IncomingCustomerDTO incomingCustomerDTO) {
        log.debug("Posting customer in controller");

        var incomingCustomer = customerInputMapper.toDomain(incomingCustomerDTO);

        var createdCustomer = createCustomer.create(incomingCustomer);

        var createdCustomerDTO = customerInputMapper.toDTO(createdCustomer);
        var createdCustomerLocation = URI.create("/customer/" + createdCustomer.id());

        return ResponseEntity.created(createdCustomerLocation)
                .body(createdCustomerDTO);
    }

    @Operation(summary = "Get customer", responses = {
            @ApiResponse(description = "Customer", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(description = "Customer not found error", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(
            @Parameter(description = "Customer id", required = true)
            @PathVariable("id") Long id) {
        log.debug("Getting customer in controller: " + id);

        var customer = getCustomer.getBy(CustomerId.from(id));

        var customerDTO = customerInputMapper.toDTO(customer);

        return ResponseEntity.ok(customerDTO);
    }

    @Operation(summary = "Update customer", responses = {
            @ApiResponse(description = "Updated customer", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(description = "Customer not found error", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> putCustomer(
            @Parameter(description = "Customer id", required = true) @PathVariable("id") Long id,
            @RequestBody IncomingCustomerDTO incomingCustomerDTO) {
        log.debug("Updating customer in controller: " + id);

        var incomingCustomer = customerInputMapper.toDomain(id, incomingCustomerDTO);

        var updatedCustomer = updateCustomer.update(incomingCustomer);
        var updatedCustomerDTO = customerInputMapper.toDTO(updatedCustomer);

        return ResponseEntity.ok(updatedCustomerDTO);
    }

    @Operation(summary = "Delete customer", responses = {
            @ApiResponse(description = "Deleted customer", responseCode = "204"),
            @ApiResponse(description = "Customer not found error", responseCode = "404", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(
            @Parameter(description = "Customer id", required = true) @PathVariable("id") Long id) {
        log.debug("Deleting customer in controller: " + id);

        deleteCustomer.delete(CustomerId.from(id));

        return ResponseEntity.noContent().build();
    }

}
