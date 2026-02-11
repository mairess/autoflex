package com.autoflex.backend.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreationDto(
    String code,
    String name,
    BigDecimal price,
    List<ProductRawMaterialCreationDto> rawMaterials
) {

}