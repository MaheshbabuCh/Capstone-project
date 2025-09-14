package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.FakeStoreProductResponseDto;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto =  restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductResponseDto.class);

        if (fakeStoreProductResponseDto.getStatusCode() != HttpStatus.OK || fakeStoreProductResponseDto.getBody() == null) {
            return null;
        }

        return getProduct(fakeStoreProductResponseDto);
    }

    private static Product getProduct(ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto) {
        Product product = new Product();

        product.setId(fakeStoreProductResponseDto.getBody().getId());
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
