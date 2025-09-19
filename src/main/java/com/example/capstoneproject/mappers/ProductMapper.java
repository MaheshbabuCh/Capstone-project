package com.example.capstoneproject.mappers;

import com.example.capstoneproject.client.FakeStoreProductRequestDto;
import com.example.capstoneproject.client.FakeStoreProductResponseDto;
import com.example.capstoneproject.dtos.ProductRequestdto;
import com.example.capstoneproject.dtos.ProductResponseDto;
import com.example.capstoneproject.models.Category;
import com.example.capstoneproject.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public FakeStoreProductRequestDto fromProductRequestdtoToFakeStoreProductRequestDto(ProductRequestdto productRequestdto) {

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

    public FakeStoreProductRequestDto fromProductToFakeStoreProductRequestDto(Product product) {

        // Only set fields if they exist in ProductRequestdto
        FakeStoreProductRequestDto fakeStoreProductRequestDto = new FakeStoreProductRequestDto();

        if (product.getId() != 0) {
            fakeStoreProductRequestDto.setId(product.getId());
        }
        if (product.getTitle() != null) {
            fakeStoreProductRequestDto.setTitle(product.getTitle());
        }
        if (product.getPrice() != 0) {
            fakeStoreProductRequestDto.setPrice(product.getPrice());
        }
        if (product.getDescription() != null) {
            fakeStoreProductRequestDto.setDescription(product.getDescription());
        }
        // Only set image if getImageUrl() exists and is not null
        if (product.getImageUrl() != null) {
            fakeStoreProductRequestDto.setImage(product.getImageUrl());
        }
        if (product.getCategory() != null && product.getCategory().getName() != null) {
            fakeStoreProductRequestDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductRequestDto;
    }

    private static Product getProduct(ResponseEntity<FakeStoreProductResponseDto> fakeStoreProductResponseDto) {
        Product product = new Product();

        /*product.setId(Objects.requireNonNull(fakeStoreProductResponseDto.getBody()).getId());
        product.setTitle(fakeStoreProductResponseDto.getBody().getTitle());
        Category category = new Category();
        category.setName(fakeStoreProductResponseDto.getBody().getCategory());
        product.setCategory(category);
        product.setPrice(fakeStoreProductResponseDto.getBody().getPrice());
        product.setDescription(fakeStoreProductResponseDto.getBody().getDescription());
        product.setImageUrl(fakeStoreProductResponseDto.getBody().getImage());*/

        FakeStoreProductResponseDto body = fakeStoreProductResponseDto.getBody();
        if (body != null) {
            if (body.getId() != null) {
                product.setId(body.getId());
            }
            if (body.getTitle() != null) {
                product.setTitle(body.getTitle());
            }
            if (body.getCategory() != null) {
                Category category = new Category();
                category.setName(body.getCategory());
                product.setCategory(category);
            }
            if (body.getPrice() != null) {
                product.setPrice(body.getPrice());
            }
            if (body.getDescription() != null) {
                product.setDescription(body.getDescription());
            }
            if (body.getImage() != null) {
                product.setImageUrl(body.getImage());
            }
        }
        return product;
    }

    public Product fromFakeStoreProductResponseDtoToModelProduct(FakeStoreProductResponseDto fakeStoreProductResponseDto) {
        Product product = new Product();
        if(fakeStoreProductResponseDto == null) {
            return product; // or throw an exception if preferred
        }
        if(fakeStoreProductResponseDto.getId() != null) {
            product.setId(fakeStoreProductResponseDto.getId());
        }
        if(fakeStoreProductResponseDto.getTitle() != null) {
            product.setTitle(fakeStoreProductResponseDto.getTitle());
        }
        if(fakeStoreProductResponseDto.getPrice() != null) {
            product.setPrice(fakeStoreProductResponseDto.getPrice());
        }
        if (fakeStoreProductResponseDto.getDescription() != null) {
            product.setDescription(fakeStoreProductResponseDto.getDescription());
        }
        if(fakeStoreProductResponseDto.getImage() != null) {
            product.setImageUrl(fakeStoreProductResponseDto.getImage());
        }
        // Note: Category mapping is handled here, it should be done elsewhere
        Category category = new Category();
        if(fakeStoreProductResponseDto.getCategory() != null) {
            category.setName(fakeStoreProductResponseDto.getCategory());
        }
        product.setCategory(category);
        return product;
    }

    public ProductResponseDto fromProductToProductResponseDto(Product product) {
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

    public Product fromProductRequestdtoToModelProduct(ProductRequestdto productRequestdto) {
        Product product = new Product();
        if(productRequestdto == null) {
            return product; // or throw an exception if preferred
        }

        if(productRequestdto.getName() != null) {
            product.setTitle(productRequestdto.getName());
        }
        if(productRequestdto.getPrice() != null) {
            product.setPrice(productRequestdto.getPrice());
        }
        if (productRequestdto.getDescription() != null) {
            product.setDescription(productRequestdto.getDescription());
        }
        if(productRequestdto.getImageUrl() != null) {
            product.setImageUrl(productRequestdto.getImageUrl());
        }
        // Note: Category mapping is handled here, it should be done elsewhere
        Category category = new Category();
        if(productRequestdto.getCategoryName() != null) {
            category.setName(productRequestdto.getCategoryName());
        }
        product.setCategory(category);
        return product;
    }

}
