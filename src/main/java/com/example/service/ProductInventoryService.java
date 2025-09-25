package com.example.service;

import com.example.dto.ProductInventoryDto;
import com.example.entity.ProductInventory;
import com.example.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryRepository inventoryRepository;

    public List<ProductInventoryDto> getAll() {
        return inventoryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<ProductInventoryDto> getById(Long id) {
        return inventoryRepository.findById(id).map(this::toDto);
    }

    public ProductInventoryDto create(ProductInventoryDto dto) {
        ProductInventory entity = new ProductInventory();
        entity.setQuantity(dto.getQuantity());
        ProductInventory saved = inventoryRepository.save(entity);
        return toDto(saved);
    }

    public ProductInventoryDto update(Long id, ProductInventoryDto dto) {
        ProductInventory entity = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (dto.getQuantity() != null) entity.setQuantity(dto.getQuantity());
        ProductInventory saved = inventoryRepository.save(entity);
        return toDto(saved);
    }

    public void delete(Long id) {
        inventoryRepository.deleteById(id);
    }

    private ProductInventoryDto toDto(ProductInventory entity) {
        ProductInventoryDto dto = new ProductInventoryDto();
        dto.setId(entity.getId());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }
}

