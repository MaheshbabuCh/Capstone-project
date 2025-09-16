package com.example.capstoneproject.mappers;

import com.example.capstoneproject.client.FakeStoreProductRequestDto;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductRequestdto;
import com.example.capstoneproject.dtos.ProductResponseDto;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public FakeStoreProductRequestDto toFakeStoreProductRequestDto(ProductRequestdto productRequestdto) {

// Only set fields if they exist in ProductRequestdto
        FakeStoreProductRequestDto fakeStoreProductRequestDto = new FakeStoreProductRequestDto();

        if (productRequestdto.getId() != null) {
            fakeStoreProductRequestDto.setId(productRequestdto.getId());
        }
        if (productRequestdto.getName() != null) {
            fakeStoreProductRequestDto.setTitle(productRequestdto.getName());
        }
        if (productRequestdto.getPrice() != null) {
            fakeStoreProductRequestDto.setPrice(productRequestdto.getPrice());
        }
        if (productRequestdto.getDescription() != null) {
            fakeStoreProductRequestDto.setDescription(productRequestdto.getDescription());
        }
// Only set image if getImageUrl() exists and is not null
        if (productRequestdto.getImageUrl() != null) {
            fakeStoreProductRequestDto.setImage(productRequestdto.getImageUrl());
        }
        if (productRequestdto.getCategoryName() != null) {
            fakeStoreProductRequestDto.setCategory(productRequestdto.getCategoryName());
        }


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

    public ProductResponseDto toProductResponseDtoFromProduct(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            dto.setCategory(product.getCategory().getName());
        } else {
            dto.setCategory(null); // or set a default value if needed
        }
        return dto;
    }

}
