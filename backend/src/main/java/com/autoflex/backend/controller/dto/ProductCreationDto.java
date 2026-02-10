package com.autoflex.backend.controller.dto;

import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import java.math.BigDecimal;
import java.util.List;

public record ProductCreationDto(
    String code,
    String name,
    BigDecimal price,
    List<ProductRawMaterial> rawMaterials
) {

  public Product toEntity() {
    return new Product(code, name, price, rawMaterials);
  }

}