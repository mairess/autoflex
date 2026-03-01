package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Product response dto.
 */
public record ProductResponseDto(
    Long id,
    String code,
    String name,
    BigDecimal price,
    List<ProductRawMaterialResponseDto> rawMaterials
) {

  /**
   * From entity product response dto.
   *
   * @param product the product
   * @return the product response dto
   */
  public static ProductResponseDto fromEntity(Product product) {
    return new ProductResponseDto(
        product.getId(),
        product.getCode(),
        product.getName(),
        product.getPrice(),
        product.getRawMaterials() == null
            ? List.of()
            : product.getRawMaterials()
                .stream()
                .map(ProductRawMaterialResponseDto::fromEntity)
                .toList()
    );
  }
}