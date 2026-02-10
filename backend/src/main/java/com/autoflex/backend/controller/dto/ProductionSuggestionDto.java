package com.autoflex.backend.controller.dto;

import java.math.BigDecimal;

public record ProductionSuggestionDto(
    Long productId,
    String productName,
    BigDecimal unitPrice,
    BigDecimal quantityProduced,
    BigDecimal totalValue
) {
  
}