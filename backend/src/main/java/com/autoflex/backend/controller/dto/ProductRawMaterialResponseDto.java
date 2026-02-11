package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.ProductRawMaterial;
import java.math.BigDecimal;

public record ProductRawMaterialResponseDto(
    Long rawMaterialId,
    String rawMaterialName,
    BigDecimal requiredQuantity
) {
  public static ProductRawMaterialResponseDto fromEntity(ProductRawMaterial productRawMaterial) {
    return new ProductRawMaterialResponseDto(
        productRawMaterial.getRawMaterial().getId(),
        productRawMaterial.getRawMaterial().getName(),
        productRawMaterial.getRequiredQuantity()
    );
  }
}