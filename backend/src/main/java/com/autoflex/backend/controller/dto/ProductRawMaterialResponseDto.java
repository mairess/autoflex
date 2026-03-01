package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.ProductRawMaterial;
import java.math.BigDecimal;

/**
 * The type Product raw material response dto.
 */
public record ProductRawMaterialResponseDto(
    Long rawMaterialId,
    String rawMaterialName,
    BigDecimal requiredQuantity
) {

  /**
   * From entity product raw material response dto.
   *
   * @param productRawMaterial the product raw material
   * @return the product raw material response dto
   */
  public static ProductRawMaterialResponseDto fromEntity(ProductRawMaterial productRawMaterial) {
    return new ProductRawMaterialResponseDto(
        productRawMaterial.getRawMaterial().getId(),
        productRawMaterial.getRawMaterial().getName(),
        productRawMaterial.getRequiredQuantity()
    );
  }
}