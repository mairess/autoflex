package com.autoflex.backend.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record ProductCreationDto(

    @NotBlank(message = "Product code is required")
    @Size(max = 50, message = "Product code must have at most 50 characters")
    String code,

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    String name,

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than zero")
    BigDecimal price,

    @NotEmpty(message = "Product must have at least one raw material")
    @Valid
    List<ProductRawMaterialCreationDto> rawMaterials
) {

}