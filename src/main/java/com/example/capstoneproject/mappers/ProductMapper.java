package com.example.capstoneproject.mappers;

import com.example.capstoneproject.client.FakeStoreProductRequestDto;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductRequestdto;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public FakeStoreProductRequestDto toFakeStoreProductRequestDto(ProductRequestdto productRequestdto) {

        FakeStoreProductRequestDto fakeStoreProductRequestDto = new FakeStoreProductRequestDto();
        fakeStoreProductRequestDto.setId(productRequestdto.getId());
        fakeStoreProductRequestDto.setTitle(productRequestdto.getName());
        fakeStoreProductRequestDto.setPrice(productRequestdto.getPrice());
        fakeStoreProductRequestDto.setDescription(productRequestdto.getDescription());
        fakeStoreProductRequestDto.setImage(productRequestdto.getImageUrl());
        fakeStoreProductRequestDto.setCategory(productRequestdto.getCategoryName());

        return fakeStoreProductRequestDto;
    }

    public Product toModelProduct(FakeStoreProductResponseDto fakeStoreProductResponseDto) {
        Product product = new Product();
        product.setId(fakeStoreProductResponseDto.getId());
        product.setTitle(fakeStoreProductResponseDto.getTitle());
        product.setPrice(fakeStoreProductResponseDto.getPrice());
        product.setDescription(fakeStoreProductResponseDto.getDescription());
        product.setImageUrl(fakeStoreProductResponseDto.getImage());
        // Note: Category mapping is handled here, it should be done elsewhere
        Category category = new Category();
        category.setName(fakeStoreProductResponseDto.getCategory());
        product.setCategory(category);
        return product;
    }

}
