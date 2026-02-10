package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.RawMaterial;
import java.math.BigDecimal;

public record RawMaterialCreationDto(
    String code,
    String name,
    BigDecimal stockQuantity
) {

  public RawMaterial toEntity() {
    return new RawMaterial(code, name, stockQuantity);
  }
}