package ua.eva.wims.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ua.eva.wims.dto.*;
import ua.eva.wims.entity.ProductEntity;
import ua.eva.wims.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    private ModelMapper mapper;

    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        ProductEntity product = productRepository.findByProductNameIgnoreCase(productCreateDto.getProductName());
        if (product != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with name " + productCreateDto.getProductName() + " already exists");
        }
        ProductEntity newProduct = productRepository.save(toEntity(productCreateDto));
        return toDto(newProduct);
    }

    public ProductResponse getAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductEntity> products = productRepository.findAll(pageable);
        return getProductResponse(products);
    }

    public ProductFullDto getProductById(Integer productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return toFullDto(productEntity);
    }

    public ProductDto updateProduct(int id, ProductUpdateDto productUpdateDto) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));

        product.setProductName(productUpdateDto.getProductName());
        product.setDescription(productUpdateDto.getDescription());
        product.setProducingCountry(productUpdateDto.getProducingCountry());
        product.setPrice(productUpdateDto.getPrice());
        product.setAmount(productUpdateDto.getAmount());

        return toDto(productRepository.save(product));
    }

    public ProductDto patchProduct(int id, ProductPatchDto productPatchDto) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));

        if (productPatchDto.getPrice() != null) {
            product.setPrice(productPatchDto.getPrice());
        }
        if (productPatchDto.getAmount() != null) {
            product.setAmount(productPatchDto.getAmount());
        }

        return toDto(productRepository.save(product));
    }

    public void deleteProduct(int id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product could not be delete"));
        productRepository.delete(productEntity);
    }

    public List<ProductDto> findProductAccordingRequestParameters(String name,
                                                                  Double minPrice,
                                                                  Double maxPrice) {
        List<ProductDto> products;
        if (name != null && minPrice != null && maxPrice != null) {
            products = findProductsByNameAndPriceRange(name, minPrice, maxPrice);
        } else if (name != null) {
            products = findProductsByName(name);
        } else {
            products = findProductsByName("");
        }
        return products;
    }

    public List<ProductDto> findProductsByName(String name) {
        List<ProductEntity> products = productRepository.findByProductNameContainingIgnoreCase(name);
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    public List<ProductDto> findProductsByNameAndPriceRange(String name, double minPrice, double maxPrice) {
        List<ProductEntity> products = productRepository.findByNameAndPriceRange(name, minPrice, maxPrice);
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    private ProductResponse getProductResponse(Page<ProductEntity> products) {
        List<ProductEntity> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream().map(this::toDto).toList();
        return ProductResponse.builder()
                .content(content)
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .totalPages(products.getTotalPages())
                .last(products.isLast())
                .build();
    }

    public ProductEntity toEntity(ProductCreateDto productDto) {
        return mapper.map(productDto, ProductEntity.class);
    }

    public ProductDto toDto(ProductEntity productEntity) {
        return mapper.map(productEntity, ProductDto.class);
    }

    public ProductFullDto toFullDto(ProductEntity productEntity) {
        return mapper.map(productEntity, ProductFullDto.class);
    }
}
