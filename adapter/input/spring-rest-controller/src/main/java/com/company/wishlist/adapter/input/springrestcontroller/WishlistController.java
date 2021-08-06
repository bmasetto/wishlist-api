package com.company.wishlist.adapter.input.springrestcontroller;

import com.company.wishlist.adapter.input.springrestcontroller.dto.ErrorDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.IncomingProductDTO;
import com.company.wishlist.adapter.input.springrestcontroller.dto.WishlistDTO;
import com.company.wishlist.adapter.input.springrestcontroller.mapper.ProductInputMapper;
import com.company.wishlist.adapter.input.springrestcontroller.mapper.WishListInputMapper;
import com.company.wishlist.core.customer.CustomerId;
import com.company.wishlist.core.wishlist.AddProductsToWishList;
import com.company.wishlist.core.wishlist.DeleteProductsFromWishList;
import com.company.wishlist.core.wishlist.GetWishlist;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("customer/{customerId}/wishlist")
@Slf4j
@Tag(name = "Wishlist")
public class WishlistController {

    private final AddProductsToWishList addProductsToWishList;
    private final GetWishlist getWishlist;
    private final DeleteProductsFromWishList deleteProductFromWishlist;

    private final WishListInputMapper wishListInputMapper;
    private final ProductInputMapper productInputMapper;

    @Autowired
    WishlistController(
            final AddProductsToWishList addProductsToWishList,
            final WishListInputMapper wishListInputMapper,
            final GetWishlist getWishlist,
            final DeleteProductsFromWishList deleteProductFromWishlist,
            final ProductInputMapper productInputMapper) {
        this.addProductsToWishList = addProductsToWishList;
        this.wishListInputMapper = wishListInputMapper;
        this.getWishlist = getWishlist;
        this.deleteProductFromWishlist = deleteProductFromWishlist;
        this.productInputMapper = productInputMapper;
    }

    @Operation(
            summary = "Add products to wishlist. It receives products ids to be added to wishlist and returns " +
                    "customer updated wishlist.",
            responses = {
                    @ApiResponse(description = "Wishlist", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = WishlistDTO.class))
                    ),
                    @ApiResponse(description = "Customer not found error", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(description = "Invalid product error", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(description = "Internal server error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistDTO> postProductToWishlist(
            @Parameter(description = "Customer id", required = true) @PathVariable("customerId") final Long customerId,
            @RequestBody final List<IncomingProductDTO> incomingProducts) {
        log.debug("Posting product to wishlist: " + customerId);

        var productsIds = productInputMapper.toDomain(incomingProducts);

        var wishList = addProductsToWishList.add(CustomerId.from(customerId), productsIds);

        var wishlistDTO = wishListInputMapper.toDTO(wishList);

        return ResponseEntity.created(URI.create("/customer/" + customerId + "/wishlist"))
                .body(wishlistDTO);
    }

    @Operation(summary = "Get wishlist", responses = {
            @ApiResponse(description = "Wishlist", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = WishlistDTO.class))
            ),
            @ApiResponse(description = "Customer not found error", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistDTO> getWishlist(
            @Parameter(description = "Customer id", required = true) @PathVariable("customerId") final Long customerId
    ) {
        log.debug("Getting wishlist: " + customerId);

        var wishList = getWishlist.getBy(CustomerId.from(customerId));
        var wishlistDTO = wishListInputMapper.toDTO(wishList);

        return ResponseEntity.ok(wishlistDTO);
    }

    @Operation(
            summary = "Delete products from wishlist. It receives products ids to be deleted from wishlist and " +
                    "returns customer updated wishlist.",
            responses = {
                    @ApiResponse(description = "Wishlist", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = WishlistDTO.class))
                    ),
                    @ApiResponse(description = "Customer not found error", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    ),
                    @ApiResponse(description = "Internal server error", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                    )
            })
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistDTO> deleteProductFromWishlist(
            @Parameter(description = "Customer id", required = true) @PathVariable("customerId") final Long customerId,
            @RequestBody final List<IncomingProductDTO> productsToBeDeleted) {

        log.debug("Deleting product from wishlist: " + customerId);

        var wishList = deleteProductFromWishlist.delete(
                CustomerId.from(customerId),
                productInputMapper.toDomain(productsToBeDeleted)
        );

        var wishlistDTO = wishListInputMapper.toDTO(wishList);

        return ResponseEntity.ok(wishlistDTO);
    }

}
