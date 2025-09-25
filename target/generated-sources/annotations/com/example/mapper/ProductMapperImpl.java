package com.example.mapper;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-25T22:24:53+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Ubuntu)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setCategoryId( ProductMapper.categoryToId( product.getCategory() ) );
        productDto.setInventoryId( ProductMapper.inventoryToId( product.getInventory() ) );
        productDto.setId( product.getId() );
        productDto.setName( product.getName() );
        productDto.setDesc( product.getDesc() );
        productDto.setSku( product.getSku() );
        productDto.setPrice( product.getPrice() );

        return productDto;
    }

    @Override
    public Product toEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.category( ProductMapper.idToCategory( dto.getCategoryId() ) );
        product.inventory( ProductMapper.idToInventory( dto.getInventoryId() ) );
        product.name( dto.getName() );
        product.desc( dto.getDesc() );
        product.sku( dto.getSku() );
        product.price( dto.getPrice() );

        return product.build();
    }

    @Override
    public void updateEntity(ProductDto dto, Product product) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCategoryId() != null ) {
            product.setCategory( ProductMapper.idToCategory( dto.getCategoryId() ) );
        }
        if ( dto.getInventoryId() != null ) {
            product.setInventory( ProductMapper.idToInventory( dto.getInventoryId() ) );
        }
        if ( dto.getName() != null ) {
            product.setName( dto.getName() );
        }
        if ( dto.getDesc() != null ) {
            product.setDesc( dto.getDesc() );
        }
        if ( dto.getSku() != null ) {
            product.setSku( dto.getSku() );
        }
        if ( dto.getPrice() != null ) {
            product.setPrice( dto.getPrice() );
        }
    }
}
