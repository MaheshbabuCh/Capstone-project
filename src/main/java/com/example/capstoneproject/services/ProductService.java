package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.dtos.FakeStoreProductDto;
import com.example.capstoneproject.models.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {

     Product getProductById(int id);

     ResponseEntity<Product> addProduct(FakeStoreProductDto requestBody);

    ExternalApiResult<?> deleteProductById(int id);
}
