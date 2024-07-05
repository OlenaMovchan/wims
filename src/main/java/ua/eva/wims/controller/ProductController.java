package ua.eva.wims.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.eva.wims.dto.*;
import ua.eva.wims.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class ProductController {

    private ProductService productService;

    @PostMapping()
    @Operation(summary = "Create product")
    @ApiResponse(responseCode = "201", description = "The newly created product")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "409", description = "Product name conflict")
    public ProductDto createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {

        return productService.createProduct(productCreateDto);
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Page of the products")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    public ProductResponse getAllProducts(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        return productService.getAllProducts(pageNo, pageSize);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by id", description = "get product by id")
    @ApiResponse(responseCode = "200", description = "Product found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductFullDto.class))})
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = {@Content})
    public ProductFullDto getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update information about product in stock.", description = "Update information about product in stock.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid product data", content = {@Content})
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Product not found", content = {@Content})
    public ProductDto updateProduct(@PathVariable Integer productId,
                                    @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(productId, productUpdateDto);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Partial update of information about product in stock.", description = "Partial update of information about product in stock.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid product data", content = {@Content})
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Product not found", content = {@Content})
    public ProductDto patchProduct(@PathVariable Integer productId,
                                   @RequestBody @Valid ProductPatchDto productPatchDto) {
        return productService.patchProduct(productId, productPatchDto);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product in stock.", description = "Delete product in stock.")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Product not found", content = {@Content})
    public void deleteProduct(@PathVariable("productId") int id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get partial or full compliance products.",
            description = "Get partial or full compliance products by name (required) and price range (optional).")
    @ApiResponse(responseCode = "200", description = "Products found")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Access denied")
    public List<ProductDto> searchProducts(
            @RequestParam String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        return productService.findProductAccordingRequestParameters(name, minPrice, maxPrice);
    }
}
