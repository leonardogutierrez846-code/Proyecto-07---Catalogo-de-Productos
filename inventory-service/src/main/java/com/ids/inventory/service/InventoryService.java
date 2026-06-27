package com.ids.inventory.service;

import com.ids.inventory.client.ProductClient;
import com.ids.inventory.dto.InventoryRequest;
import com.ids.inventory.dto.InventoryResponse;
import com.ids.inventory.dto.InventoryUpdateRequest;
import com.ids.inventory.dto.ProductResponse;
import com.ids.inventory.exception.BadRequestException;
import com.ids.inventory.exception.ResourceNotFoundException;
import com.ids.inventory.model.Inventory;
import com.ids.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    public InventoryService(InventoryRepository inventoryRepository, ProductClient productClient) {
        this.inventoryRepository = inventoryRepository;
        this.productClient = productClient;
    }

    public InventoryResponse create(InventoryRequest request) {
        productClient.findProductById(request.productId());

        if (inventoryRepository.existsByProductId(request.productId())) {
            throw new BadRequestException("Ya existe inventario para el producto con ID: " + request.productId());
        }

        Inventory inventory = new Inventory();
        inventory.setProductId(request.productId());
        inventory.setCantidadDisponible(request.cantidadDisponible());
        inventory.setUbicacion(request.ubicacion());

        Inventory savedInventory = inventoryRepository.save(inventory);
        return buildResponse(savedInventory);
    }

    public List<InventoryResponse> findAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    public InventoryResponse findByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado para el producto con ID: " + productId));

        return buildResponse(inventory);
    }

    public InventoryResponse update(Long productId, InventoryUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado para el producto con ID: " + productId));

        inventory.setCantidadDisponible(request.cantidadDisponible());
        inventory.setUbicacion(request.ubicacion());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return buildResponse(updatedInventory);
    }

    private InventoryResponse buildResponse(Inventory inventory) {
        ProductResponse product = productClient.findProductById(inventory.getProductId());
        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductId(),
                product.nombre(),
                product.precio(),
                inventory.getCantidadDisponible(),
                inventory.getUbicacion()
        );
    }
}
