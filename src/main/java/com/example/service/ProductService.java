package com.example.service;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.entity.ProductCategory;
import com.example.entity.ProductInventory;
import com.example.repository.ProductCategoryRepository;
import com.example.repository.ProductInventoryRepository;
import com.example.repository.ProductRepository;
import com.example.mapper.ProductMapper;
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

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    public ProductDto create(ProductDto dto) {
        Product entity = productMapper.toEntity(dto);
        // Resolve references from ids to managed entities if provided
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
        Product saved = productRepository.save(entity);
        return productMapper.toDto(saved);
    }

    public ProductDto update(Long id, ProductDto dto) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateEntity(dto, entity);
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
        Product saved = productRepository.save(entity);
        return productMapper.toDto(saved);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // Manual mapping methods removed in favor of MapStruct ProductMapper
}

