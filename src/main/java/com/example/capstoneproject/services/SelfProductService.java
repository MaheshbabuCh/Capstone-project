package com.example.capstoneproject.services;

import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.NotFoundException;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Primary
@Service
public class SelfProductService implements ProductService{

    ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(int id) {
      return productRepository.findProductById(id);
    }

    @Override
    public ResponseEntity<Product> addProduct(FakeStoreProductResponseDto requestBody) {
        return null;
    }

    @Override
    public ExternalApiResult<?> deleteProductById(int id) {
        productRepository.deleteById(id);
        return new ExternalApiResult<>(HttpStatusCode.valueOf(200), "Product deleted successfully", null);
    }

    @Override
    public ExternalApiResult<List<Product>> getAllProducts() {
        return null;
    }

    @Override
    public ExternalApiResult<Product> updateProduct(int id, Product product) throws BadRequestException, NotFoundException {
        return null;
    }
}
