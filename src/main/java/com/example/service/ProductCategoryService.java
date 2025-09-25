package com.example.service;

import com.example.dto.ProductCategoryDto;
import com.example.entity.ProductCategory;
import com.example.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    public List<ProductCategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ProductCategoryDto> getById(Long id) {
        return categoryRepository.findById(id).map(this::toDto);
    }

    public ProductCategoryDto create(ProductCategoryDto dto) {
        ProductCategory entity = new ProductCategory();
        entity.setName(dto.getName());
        entity.setDesc(dto.getDesc());
        ProductCategory saved = categoryRepository.save(entity);
        return toDto(saved);
    }

    public ProductCategoryDto update(Long id, ProductCategoryDto dto) {
        ProductCategory entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDesc() != null) entity.setDesc(dto.getDesc());
        ProductCategory saved = categoryRepository.save(entity);
        return toDto(saved);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private ProductCategoryDto toDto(ProductCategory entity) {
        ProductCategoryDto dto = new ProductCategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDesc(entity.getDesc());
        return dto;
    }
}

