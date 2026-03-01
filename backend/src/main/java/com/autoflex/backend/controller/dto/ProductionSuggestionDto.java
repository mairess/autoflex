package com.autoflex.backend.controller.dto;

import java.math.BigDecimal;

/**
 * The type Production suggestion dto.
 */
public record ProductionSuggestionDto(
    Long productId,
    String productName,
    BigDecimal unitPrice,
    BigDecimal quantityProduced,
    BigDecimal totalValue
) {

}