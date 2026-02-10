package com.autoflex.backend.controller.dto;

import java.math.BigDecimal;

public record ProductRawMaterialCreationDto(
    Long rawMaterialId,
    BigDecimal requiredQuantity
) {

}