package com.example.mapper;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.entity.ProductCategory;
import com.example.entity.ProductInventory;
import org.mapstruct.*;

/**
 * MapStruct mapper for Product and ProductDto.
 * - Maps nested relations to ids and vice versa via qualified methods.
 * - Ignores nulls on update to support partial updates.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "categoryId", source = "category", qualifiedByName = "categoryToId"),
            @Mapping(target = "inventoryId", source = "inventory", qualifiedByName = "inventoryToId")
    })
    ProductDto toDto(Product product);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "idToCategory"),
            @Mapping(target = "inventory", source = "inventoryId", qualifiedByName = "idToInventory")
    })
    Product toEntity(ProductDto dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "category", source = "categoryId", qualifiedByName = "idToCategory"),
            @Mapping(target = "inventory", source = "inventoryId", qualifiedByName = "idToInventory")
    })
    void updateEntity(ProductDto dto, @MappingTarget Product product);

    @Named("categoryToId")
    static Long categoryToId(ProductCategory category) {
        return category != null ? category.getId() : null;
    }

    @Named("inventoryToId")
    static Long inventoryToId(ProductInventory inventory) {
        return inventory != null ? inventory.getId() : null;
    }

    @Named("idToCategory")
    static ProductCategory idToCategory(Long id) {
        if (id == null) return null;
        ProductCategory pc = new ProductCategory();
        pc.setId(id);
        return pc;
    }

    @Named("idToInventory")
    static ProductInventory idToInventory(Long id) {
        if (id == null) return null;
        ProductInventory pi = new ProductInventory();
        pi.setId(id);
        return pi;
    }
}

