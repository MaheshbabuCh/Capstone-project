package com.example.capstoneproject.services;

import com.example.capstoneproject.client.FakeStoreApi;
import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductRequestdto;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.NotFoundException;
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
    private final FakeStoreApi fakeStoreApi;

    @Autowired
    public ProductServiceImplementation(RestTemplateBuilder restTemplateBuilder, ProductMapper productMapper, FakeStoreApi fakeStoreApi) {
        this.fakeStoreApi = fakeStoreApi;
        this.productMapper = productMapper;
        this.restTemplateBuilder = restTemplateBuilder;
    }


    @Override
    public Product getProductById(int id) {
        ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto = fakeStoreApi.getProductById(id);
        if (fakeStoreProductResponseDto.getStatusCode() != HttpStatus.OK || fakeStoreProductResponseDto.getBody() == null) {
            return null;
        }
        return getProduct(fakeStoreProductResponseDto);
    }

    @Override
    public ResponseEntity<Product> addProduct(FakeStoreProductResponseDto requestBody) {
        ResponseEntity<FakeStoreProductResponseDto> responseEntity = fakeStoreApi.addProduct(requestBody);
        Product product = getProduct(responseEntity);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(product);
    }

    @Override
    public ExternalApiResult<Void> deleteProductById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = fakeStoreApi.deleteProductById(id);
        return new ExternalApiResult<>(response.getStatusCode(), "Delete operation completed", null);
    }

    @Override
    public ExternalApiResult<List<Product>> getAllProducts() {

        ResponseEntity<FakeStoreProductResponseDto[]> responseEntity = fakeStoreApi.getAllProducts();

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            List<Product> products = new ArrayList<>();
            for (FakeStoreProductResponseDto dto : responseEntity.getBody()) {
                Product product = productMapper.toModelProduct(dto);
                products.add(product);
            }
            return new ExternalApiResult<>(responseEntity.getStatusCode(), "Get all products", products);
        }
        return new ExternalApiResult<>(responseEntity.getStatusCode(), "No products are available", null);
    }

    @Override
    public ExternalApiResult<Product> updateProduct(int id, Product product) throws BadRequestException, NotFoundException {

        ResponseEntity<FakeStoreProductResponseDto> responseEntity = fakeStoreApi.updateProduct(id, product);

        if(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST || responseEntity.getStatusCode().is4xxClientError()){
            throw new BadRequestException("Bad Request");
        }
        if(responseEntity.getBody() == null) {
            throw new NotFoundException("Product not found");
        }

        if(responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getBody() != null){
            Product product1 =  productMapper.toModelProduct(responseEntity.getBody());
            return new ExternalApiResult<>(responseEntity.getStatusCode(), "Product updated successfully", product1);
        }

        return  new ExternalApiResult<>(responseEntity.getStatusCode(), "Unexpected error happened", null);
    }


    private static Product getProduct(ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto) {
        Product product = new Product();

        FakeStoreProductResponseDto body = fakeStoreProductResponseDto.getBody();
        if (body != null) {
            if (body.getId() != null) {
                product.setId(body.getId());
            }
            if (body.getTitle() != null) {
                product.setTitle(body.getTitle());
            }
            if (body.getCategory() != null) {
                Category category = new Category();
                category.setName(body.getCategory());
                product.setCategory(category);
            }
            if (body.getPrice() != null) {
                product.setPrice(body.getPrice());
            }
            if (body.getDescription() != null) {
                product.setDescription(body.getDescription());
            }
            if (body.getImage() != null) {
                product.setImageUrl(body.getImage());
            }
        }
        return product;
    }
}
