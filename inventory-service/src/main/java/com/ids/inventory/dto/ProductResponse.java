package com.ids.inventory.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String categoria
) {
}
