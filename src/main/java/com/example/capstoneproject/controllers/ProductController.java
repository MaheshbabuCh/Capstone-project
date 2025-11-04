package com.example.capstoneproject.controllers;

import com.example.capstoneproject.clients.authenticationclient.AuthenticationClient;
import com.example.capstoneproject.dtos.ExternalApiResult;
import com.example.capstoneproject.clients.fakeStoreClient.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductResponseDto;
import com.example.capstoneproject.exceptions.BadRequestException;
import com.example.capstoneproject.exceptions.InvalidSessionException;
import com.example.capstoneproject.exceptions.NotFoundException;
import com.example.capstoneproject.mappers.ProductMapper;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.repositories.ProductRepository;
import com.example.capstoneproject.services.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final ProductMapper productMapper;
    ProductService productService;
    ProductRepository productRepository;
    AuthenticationClient authenticationClient;


    @Autowired
    //@Qualifier("selfProductService")
    public ProductController(ProductService productService, ProductMapper productMapper, ProductRepository productRepository, AuthenticationClient authenticationClient) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.authenticationClient = authenticationClient;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int id) throws NotFoundException {
        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        if (product == null) {
            /*productResponseDto.setMessage("Product not found");
            productResponseDto.setErrorCode(HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productResponseDto);*/
            throw new NotFoundException("Product not found");
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

//        Product product = new Product();
//        product.setDescription(requestBody.getDescription());
//        product.setTitle(requestBody.getTitle());
//        product.setPrice(requestBody.getPrice());
//        product.setImageUrl(requestBody.getImage());
//        // Assuming Category is another entity, you might need to fetch it from DB or create a new one
//        // Here, we're just creating a new Category for demonstration purposes
//        Category category = new Category();
//        category.setName(requestBody.getCategory());
//        product.setCategory(category);
//
//        Product savedProduct = productRepository.save(product);
//
//       return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);




        //return productService.addProduct(requestBody);

        ResponseEntity<Product> responseProductEntity = productService.addProduct(requestBody);
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
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ExternalApiResult<?>> deleteProductById(@PathVariable int id) {
        // Map<String, String> uriVariables = Map.of("id", String.valueOf(id), "isDeleted", "true");

        ExternalApiResult<?> result = productService.deleteProductById(id);

        return ResponseEntity.status(result.getStatus()).body(result);

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@Nullable @RequestHeader("AUTH_TOKEN") String token, @Nullable @RequestHeader("USER_ID") Long userId) throws Exception {

        if(userId == null || token == null || token.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }



        boolean isSuccess =  authenticationClient.validate(userId, token);

        if(!isSuccess){
           throw new InvalidSessionException("Invalid session. Please log in again.");
        }

        ExternalApiResult<List<Product>> externalApiResult = productService.getAllProducts();

        if (externalApiResult.getStatus() != HttpStatus.OK || externalApiResult.getBody() == null) {
          //  return ResponseEntity.status(externalApiResult.getStatus()).body(externalApiResult.getBody());
            throw new NotFoundException("Products not found");
        }

            List<Product> products = externalApiResult.getBody();
            List<ProductResponseDto> productResponseDtos = new ArrayList<>();

            for (Product product : products) {
                ProductResponseDto dto = productMapper.fromProductToProductResponseDto(product);
                productResponseDtos.add(dto);
            }
            return ResponseEntity.status(externalApiResult.getStatus()).body(productResponseDtos);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) throws NotFoundException, BadRequestException {

       /* ExternalApiResult<Product> externalApiResult =  productService.updateProduct(id, productRequestdto);
        if(externalApiResult.getStatus() != HttpStatus.OK || externalApiResult.getBody() == null) {
           // return ResponseEntity.status(externalApiResult.getStatus()).body(externalApiResult.getBody());
            throw new NotFoundException("Product not found");
        }
        ProductResponseDto responseDto =  productMapper.toProductResponseDtoFromProduct(externalApiResult.getBody());
        return ResponseEntity.status(externalApiResult.getStatus()).body(responseDto);*/

        try {
            Product updatedProduct = productService.updateProduct(id, product).getBody();
            if (updatedProduct == null) {
                throw new NotFoundException("Product not found");
            }
            ProductResponseDto responseDto = productMapper.fromProductToProductResponseDto(updatedProduct);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }



}