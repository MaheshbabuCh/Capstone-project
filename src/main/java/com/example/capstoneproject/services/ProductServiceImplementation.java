package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.mappers.ProductMapper;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ProductServiceImplementation implements ProductService {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImplementation(RestTemplateBuilder restTemplateBuilder, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.restTemplateBuilder = restTemplateBuilder;
    }


    @Override
    public Product getProductById(int id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductResponseDto.class);

        if (fakeStoreProductResponseDto.getStatusCode() != HttpStatus.OK || fakeStoreProductResponseDto.getBody() == null) {
            return null;
        }
        return getProduct(fakeStoreProductResponseDto);
    }

    @Override
    public ResponseEntity<Product> addProduct(FakeStoreProductResponseDto requestBody) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDto> responseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, FakeStoreProductResponseDto.class);
        Product product = getProduct(responseEntity);

        return ResponseEntity.status(responseEntity.getStatusCode()).body(product);
    }

    @Override
    public ExternalApiResult<Void> deleteProductById(int id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = restTemplate.exchange("https://fakestoreapi.com/products/" + id, org.springframework.http.HttpMethod.DELETE, null, Void.class);

        return new ExternalApiResult<>(response.getStatusCode(), "Delete operation completed", null);
    }

    @Override
    public ExternalApiResult<List<Product>> getAllProducts() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, FakeStoreProductResponseDto[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {

            List<Product> products = new ArrayList<>();

            for (FakeStoreProductResponseDto dto : responseEntity.getBody()) {
                Product product = productMapper.toModelProduct(dto);
                /*
                product.setId(dto.getId());
                product.setTitle(dto.getTitle());
                Category category = new Category();
                category.setName(dto.getCategory());
                product.setCategory(category);
                product.setPrice(dto.getPrice());
                product.setDescription(dto.getDescription());
                product.setImageUrl(dto.getImage()); */
                products.add(product);
            }
            return new ExternalApiResult<>(responseEntity.getStatusCode(), "Get all products", products);
        }
        return new ExternalApiResult<>(responseEntity.getStatusCode(), "No products are available", null);
    }

    private static Product getProduct(ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto) {
        Product product = new Product();

        product.setId(Objects.requireNonNull(fakeStoreProductResponseDto.getBody()).getId());
        product.setTitle(fakeStoreProductResponseDto.getBody().getTitle());
        Category category = new Category();
        category.setName(fakeStoreProductResponseDto.getBody().getCategory());
        product.setCategory(category);
        product.setPrice(fakeStoreProductResponseDto.getBody().getPrice());
        product.setDescription(fakeStoreProductResponseDto.getBody().getDescription());
        product.setImageUrl(fakeStoreProductResponseDto.getBody().getImage());
        return product;
    }
}
