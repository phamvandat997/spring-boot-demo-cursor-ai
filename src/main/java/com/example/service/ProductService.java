package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.entity.ProductCategory;
import com.example.entity.ProductInventory;
import com.example.repository.ProductCategoryRepository;
import com.example.repository.ProductInventoryRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private ProductInventoryRepository inventoryRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id).map(this::toDto);
    }

    public ProductDto create(ProductDto dto) {
        Product entity = new Product();
        copyToEntity(dto, entity);
        Product saved = productRepository.save(entity);
        return toDto(saved);
    }

    public ProductDto update(Long id, ProductDto dto) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        copyToEntity(dto, entity);
        Product saved = productRepository.save(entity);
        return toDto(saved);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private void copyToEntity(ProductDto dto, Product entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDesc() != null) entity.setDesc(dto.getDesc());
        if (dto.getSku() != null) entity.setSku(dto.getSku());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(category);
        }

        if (dto.getInventoryId() != null) {
            ProductInventory inventory = inventoryRepository.findById(dto.getInventoryId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));
            entity.setInventory(inventory);
        }
    }

    private ProductDto toDto(Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDesc(entity.getDesc());
        dto.setSku(entity.getSku());
        dto.setPrice(entity.getPrice());
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
        dto.setInventoryId(entity.getInventory() != null ? entity.getInventory().getId() : null);
        return dto;
    }
}

