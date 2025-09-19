package com.example.capstoneproject.client;

import com.example.capstoneproject.mappers.ProductMapper;
import com.example.capstoneproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApi {

    private final RestTemplateBuilder restTemplateBuilder;
    private final ProductMapper productMapper;

    @Autowired
    public FakeStoreApi( RestTemplateBuilder restTemplateBuilder1, ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.restTemplateBuilder = restTemplateBuilder1;
    }


   public ResponseEntity<FakeStoreProductResponseDto> getProductById(int id) {
       RestTemplate restTemplate = restTemplateBuilder.build();
       return restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductResponseDto.class);
   }

   public ResponseEntity<FakeStoreProductResponseDto> addProduct(FakeStoreProductResponseDto requestBody){
       RestTemplate restTemplate = restTemplateBuilder.build();
       return restTemplate.postForEntity("https://fakestoreapi.com/products", requestBody, FakeStoreProductResponseDto.class);
   }

   public ResponseEntity<Void> deleteProductById(int id){
       RestTemplate restTemplate = restTemplateBuilder.build();
       return  restTemplate.exchange("https://fakestoreapi.com/products/" + id, org.springframework.http.HttpMethod.DELETE, null, Void.class);
   }

    public ResponseEntity<FakeStoreProductResponseDto[]> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products";
        return restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, null, FakeStoreProductResponseDto[].class);
    }

    public ResponseEntity<FakeStoreProductResponseDto> updateProduct(int id, Product product) {
       /* RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products/" + id;
        ResponseEntity<FakeStoreProductResponseDto> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(productMapper.toFakeStoreProductRequestDto(productRequestdto)),
                FakeStoreProductResponseDto.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            Product product = getProduct(responseEntity);
            return new ExternalApiResult<>(responseEntity.getStatusCode(), "Product updated successfully", product);
        }
        return new ExternalApiResult<>(responseEntity.getStatusCode(), "Unable to update product", null);*/
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products/" + id;
        return restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(productMapper.fromProductToFakeStoreProductRequestDto(product)),
                FakeStoreProductResponseDto.class
        );
    }

}