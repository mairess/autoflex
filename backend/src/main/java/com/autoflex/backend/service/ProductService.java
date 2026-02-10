package com.autoflex.backend.service;

import com.autoflex.backend.controller.dto.ProductionSuggestionDto;
import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import com.autoflex.backend.repository.ProductRepository;
import com.autoflex.backend.service.exception.ProductAlreadyExistsException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The type Product service.
 */
@Service
public class ProductService {

  private final ProductRepository productRepository;

  /**
   * Instantiates a new Product service.
   *
   * @param productRepository the product repository
   */
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  /**
   * Create product product.
   *
   * @param product the product
   * @return the product
   * @throws ProductAlreadyExistsException the product already exists exception
   */
  public Product createProduct(Product product) throws ProductAlreadyExistsException {
    if (productRepository.existsByCode(product.getCode())) {
      throw new ProductAlreadyExistsException();
    }

    if (product.getRawMaterials() != null) {
      for (ProductRawMaterial productRawMaterial : product.getRawMaterials()) {
        productRawMaterial.setProduct(product);
      }
    }
    return productRepository.save(product);
  }

  /**
   * Gets product suggestion.
   *
   * @return the product suggestion
   */
  public List<ProductionSuggestionDto> getProductionSuggestions() {

    List<Product> products = productRepository.findAllWithMaterials();
    List<ProductionSuggestionDto> result = new ArrayList<>();

    for (Product product : products) {

      if (product.getRawMaterials() == null ||
          product.getRawMaterials().isEmpty()) {
        continue;
      }

      BigDecimal maxUnits = calculateMaxUnits(product);

      if (maxUnits != null &&
          maxUnits.compareTo(BigDecimal.ZERO) > 0) {

        BigDecimal totalValue =
            maxUnits.multiply(product.getPrice());

        result.add(new ProductionSuggestionDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            maxUnits,
            totalValue
        ));
      }
    }

    return result.stream()
        .sorted(
            Comparator.comparing(
                ProductionSuggestionDto::unitPrice
            ).reversed()
        )
        .toList();
  }

  private BigDecimal calculateMaxUnits(Product product) {

    BigDecimal maxUnits = null;

    for (ProductRawMaterial productRawMaterial : product.getRawMaterials()) {

      if (productRawMaterial.getRawMaterial() == null
          || productRawMaterial.getRequiredQuantity() == null) {
        continue;
      }

      BigDecimal stock =
          productRawMaterial.getRawMaterial().getStockQuantity();

      BigDecimal required =
          productRawMaterial.getRequiredQuantity();

      if (required.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      BigDecimal possible =
          stock.divide(required, 0, RoundingMode.DOWN);

      if (maxUnits == null || possible.compareTo(maxUnits) < 0) {
        maxUnits = possible;
      }
    }

    return maxUnits;
  }

}