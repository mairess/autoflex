package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDto(
    Long id,
    String code,
    String name,
    BigDecimal price,
    List<ProductRawMaterial> rawMaterials
) {

  public static ProductResponseDto fromEntity(Product product) {
    return new ProductResponseDto(
        product.getId(),
        product.getCode(),
        product.getName(),
        product.getPrice(),
        product.getRawMaterials()
    );
  }
}