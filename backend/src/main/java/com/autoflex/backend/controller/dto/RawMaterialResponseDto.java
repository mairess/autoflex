package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.RawMaterial;
import java.math.BigDecimal;

public record RawMaterialResponseDto(
    Long id,
    String code,
    String name,
    BigDecimal stockQuantity
) {

  public static RawMaterialResponseDto fromEntity(RawMaterial rawMaterial) {
    return new RawMaterialResponseDto(
        rawMaterial.getId(),
        rawMaterial.getCode(),
        rawMaterial.getName(),
        rawMaterial.getStockQuantity()
    );
  }

}