package com.autoflex.backend.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRawMaterialCreationDto(

    @NotNull(message = "Raw material id is required")
    Long rawMaterialId,

    @NotNull(message = "Required quantity is required")
    @Positive(message = "Required quantity must be greater than zero")
    BigDecimal requiredQuantity
) {

}