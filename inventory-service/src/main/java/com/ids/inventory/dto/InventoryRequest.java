package com.ids.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
        @NotNull(message = "El productId es obligatorio")
        Long productId,

        @NotNull(message = "La cantidad disponible es obligatoria")
        @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
        Integer cantidadDisponible,

        String ubicacion
) {
}
