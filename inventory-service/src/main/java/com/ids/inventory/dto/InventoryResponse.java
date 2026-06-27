package com.ids.inventory.dto;

import java.math.BigDecimal;

public record InventoryResponse(
        Long id,
        Long productId,
        String nombreProducto,
        BigDecimal precioProducto,
        Integer cantidadDisponible,
        String ubicacion
) {
}
