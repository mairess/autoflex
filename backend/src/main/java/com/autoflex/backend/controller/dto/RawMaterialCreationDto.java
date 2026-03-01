package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.RawMaterial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * The type Raw material creation dto.
 */
public record RawMaterialCreationDto(

    @NotBlank(message = "Code is required")
    @Size(max = 50, message = "Code must have at most 50 characters")
    String code,

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have at most 100 characters")
    String name,

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    BigDecimal stockQuantity
) {

  /**
   * To entity raw material.
   *
   * @return the raw material
   */
  public RawMaterial toEntity() {
    return new RawMaterial(code, name, stockQuantity);
  }
}