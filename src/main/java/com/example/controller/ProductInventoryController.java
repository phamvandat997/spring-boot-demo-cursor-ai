package com.example.controller;

import com.example.dto.ProductInventoryDto;
import com.example.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
@CrossOrigin(origins = "*")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<ProductInventoryDto>> list() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInventoryDto> detail(@PathVariable Long id) {
        Optional<ProductInventoryDto> dto = inventoryService.getById(id);
        return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductInventoryDto> create(@RequestBody ProductInventoryDto dto) {
        return ResponseEntity.ok(inventoryService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductInventoryDto> update(@PathVariable Long id, @RequestBody ProductInventoryDto dto) {
        return ResponseEntity.ok(inventoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}

