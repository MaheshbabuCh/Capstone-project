package com.example.capstoneproject.services;

import com.example.capstoneproject.clients.fakeStoreClient.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.NotFoundException;
import com.example.capstoneproject.mappers.ProductMapper;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfProductServiceImplementation implements ProductService{

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Autowired
    public SelfProductServiceImplementation(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Product getProductById(int id) {
      return productRepository.findProductById(id);
    }

    @Override
    public ResponseEntity<Product> addProduct(FakeStoreProductResponseDto requestBody) {

        Product product =  productMapper.fromFakeStoreProductResponseDtoToModelProduct(requestBody);
        Product savedProduct = productRepository.save(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @Override
    public ExternalApiResult<?> deleteProductById(int id) {
        productRepository.deleteById(id);
        return new ExternalApiResult<>(HttpStatusCode.valueOf(200), "Product deleted successfully", null);
    }

    @Override
    public ExternalApiResult<List<Product>> getAllProducts() {

        List<Product> products = productRepository.findAll();
        return new ExternalApiResult<>(HttpStatusCode.valueOf(200), "All products retrieved successfully", products);
    }

    @Override
    public ExternalApiResult<Product> updateProduct(int id, Product product) throws BadRequestException, NotFoundException {

        if(id != product.getId() && product.getId() != 0){
            throw new BadRequestException("Product ID in the path and body do not match");
        }

        Product existingProduct = productRepository.findProductById(id);
        if(existingProduct == null){
            throw new NotFoundException("Product with ID " + id + " not found");
        }

        Product copiedProduct =  productMapper.copyDataFromIncomingProductToExistingProduct(product, existingProduct);
        Product updatedProduct =  productRepository.save(copiedProduct);
        return new ExternalApiResult<>(HttpStatusCode.valueOf(200), "Product updated successfully", updatedProduct);
    }


}
