package com.example.capstoneproject.services;

import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductRequestdto;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.NotFoundException;
import com.example.capstoneproject.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

     Product getProductById(int id);

     ResponseEntity<Product> addProduct(FakeStoreProductResponseDto requestBody);

     ExternalApiResult<?> deleteProductById(int id);

     ExternalApiResult<List<Product>> getAllProducts();

     ExternalApiResult<Product> updateProduct(int id, Product product) throws BadRequestException, NotFoundException;
}
