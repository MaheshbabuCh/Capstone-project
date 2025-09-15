package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.dtos.FakeStoreProductDto;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class ProductServiceImplementation implements ProductService {
    
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public ProductServiceImplementation(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getProductById(int id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductResponseDto =  restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);

        if (fakeStoreProductResponseDto.getStatusCode() != HttpStatus.OK || fakeStoreProductResponseDto.getBody() == null) {
            return null;
        }

        return getProduct(fakeStoreProductResponseDto);
    }

    @Override
    public ResponseEntity<Product> addProduct(FakeStoreProductDto requestBody) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, FakeStoreProductDto.class);

        Product product = getProduct(responseEntity);

        return ResponseEntity.status(responseEntity.getStatusCode()).body(product);

    }

    @Override
    public ExternalApiResult<Void> deleteProductById(int id) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                org.springframework.http.HttpMethod.DELETE,
                null,
                Void.class);

        return new ExternalApiResult<>(response.getStatusCode(), "Delete operation completed", null);
    }

    private static Product getProduct(ResponseEntity<FakeStoreProductDto> fakeStoreProductResponseDto) {
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
