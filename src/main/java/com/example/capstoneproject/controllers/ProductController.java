package com.example.capstoneproject.controllers;

import com.example.capstoneproject.dtos.ProductResponseDto;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id) {
       Product product =  productService.getProductById(id);
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
}
