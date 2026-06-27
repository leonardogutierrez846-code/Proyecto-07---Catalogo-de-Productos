package com.ids.inventory.controller;

import com.ids.inventory.dto.InventoryRequest;
import com.ids.inventory.dto.InventoryResponse;
import com.ids.inventory.dto.InventoryUpdateRequest;
import com.ids.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventario", description = "Gestion de stock por producto")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @Operation(summary = "Registrar stock de un producto")
    public ResponseEntity<InventoryResponse> create(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.create(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los registros de inventario")
    public ResponseEntity<List<InventoryResponse>> findAll() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Consultar stock de un producto especifico")
    public ResponseEntity<InventoryResponse> findByProductId(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(inventoryService.findByProductId(productId));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Actualizar cantidad disponible de un producto")
    public ResponseEntity<InventoryResponse> update(@PathVariable("productId") Long productId,
                                                    @Valid @RequestBody InventoryUpdateRequest request) {
        return ResponseEntity.ok(inventoryService.update(productId, request));
    }
}
