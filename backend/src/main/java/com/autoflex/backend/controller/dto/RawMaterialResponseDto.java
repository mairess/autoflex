package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.RawMaterial;
import java.math.BigDecimal;

/**
 * The type Raw material response dto.
 */
public record RawMaterialResponseDto(
    Long id,
    String code,
    String name,
    BigDecimal stockQuantity
) {

  /**
   * From entity raw material response dto.
   *
   * @param rawMaterial the raw material
   * @return the raw material response dto
   */
  public static RawMaterialResponseDto fromEntity(RawMaterial rawMaterial) {
    return new RawMaterialResponseDto(
        rawMaterial.getId(),
        rawMaterial.getCode(),
        rawMaterial.getName(),
        rawMaterial.getStockQuantity()
    );
  }

}