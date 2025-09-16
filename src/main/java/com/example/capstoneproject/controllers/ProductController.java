package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductResponseDto;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    ProductService productService;


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        if (product == null) {
            productResponseDto.setMessage("Product not found");
            productResponseDto.setErrorCode(HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResponseDto);
        }

        productResponseDto.setId(product.getId());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setCategory(product.getCategory().getName());

        return ResponseEntity.ok(productResponseDto);

        // return productResponseDto;
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody FakeStoreProductResponseDto requestBody) {

        return productService.addProduct(requestBody);

        /* ResponseEntity<Product> responseProductEntity = productService.addProduct(requestBody);
        ProductResponseDto productResponseDto = new ProductResponseDto();

        if (responseProductEntity.getStatusCode().is2xxSuccessful() && responseProductEntity.getBody() != null) {
            productResponseDto.setId(responseProductEntity.getBody().getId());
            productResponseDto.setDescription(responseProductEntity.getBody().getDescription());
            productResponseDto.setTitle(responseProductEntity.getBody().getTitle());
            productResponseDto.setPrice(responseProductEntity.getBody().getPrice());
            productResponseDto.setImageUrl(responseProductEntity.getBody().getImageUrl());
            productResponseDto.setCategory(responseProductEntity.getBody().getCategory().getName());
            productResponseDto.setMessage("Product added successfully");

            return ResponseEntity.ok(productResponseDto);
        }else {
            productResponseDto.setMessage("Failed to add product");
            productResponseDto.setErrorCode(responseProductEntity.getStatusCode().toString());
            return ResponseEntity.status(responseProductEntity.getStatusCode()).body(productResponseDto);
        } */
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ExternalApiResult<?>> deleteProductById(@PathVariable int id) {
        // Map<String, String> uriVariables = Map.of("id", String.valueOf(id), "isDeleted", "true");

        ExternalApiResult<?> result = productService.deleteProductById(id);

        return ResponseEntity.status(result.getStatus()).body(result);

    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {

        ExternalApiResult<List<Product>> externalApiResult = productService.getAllProducts();

        if (externalApiResult.getStatus() != HttpStatus.OK || externalApiResult.getBody() == null) {
            return ResponseEntity.status(externalApiResult.getStatus()).body(externalApiResult.getBody());
        }

            List<Product> products = externalApiResult.getBody();

            List<ProductResponseDto> productResponseDtos = new ArrayList<>();

            for (Product product : products) {
                ProductResponseDto dto = new ProductResponseDto();
                dto.setId(product.getId());
                dto.setTitle(product.getTitle());
                dto.setDescription(product.getDescription());
                dto.setPrice(product.getPrice());
                dto.setImageUrl(product.getImageUrl());
                dto.setCategory(product.getCategory().getName());

                productResponseDtos.add(dto);
            }

            return ResponseEntity.status(externalApiResult.getStatus()).body(productResponseDtos);

    }



}