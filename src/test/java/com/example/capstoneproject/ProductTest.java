package com.example.capstoneproject;

import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import com.example.capstoneproject.repositories.CategoryRepository;
import com.example.capstoneproject.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class ProductTest {

   // @Autowired
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Autowired
   public ProductTest(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
   }


    @Test
    @Transactional
    @Rollback(false)
    public  void createProduct(){

for(int i=1;i<=5;i++) {
    Product product = new Product();
    // product.setId(1);
    product.setTitle("Jewellery Product");
    product.setPrice(99.99f);
    product.setDescription("This is a beautiful product");
    product.setImageUrl("http://example.com/image.jpg");
    Category category = new Category();
    // category.setId(1);
    category.setName(" Jewellery");
    category.setDescription("This is a jewellery category");
    product.setCategory(category);
    productRepository.save(product);
}



    }
}
