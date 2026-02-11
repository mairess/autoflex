package com.autoflex.backend.service;

import com.autoflex.backend.controller.dto.ProductCreationDto;
import com.autoflex.backend.controller.dto.ProductRawMaterialCreationDto;
import com.autoflex.backend.controller.dto.ProductionSuggestionDto;
import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.repository.ProductRepository;
import com.autoflex.backend.service.exception.ProductAlreadyExistsException;
import com.autoflex.backend.service.exception.ProductNotFoundException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * The type Product service.
 */
@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final RawMaterialService rawMaterialService;

  /**
   * Instantiates a new Product service.
   *
   * @param productRepository  the product repository
   * @param rawMaterialService the raw material service
   */
  public ProductService(ProductRepository productRepository,
      RawMaterialService rawMaterialService) {
    this.productRepository = productRepository;
    this.rawMaterialService = rawMaterialService;
  }


  /**
   * Create product.
   *
   * @param productCreationDto the product creation dto
   * @return the product
   * @throws ProductAlreadyExistsException the product already exists exception
   * @throws RawMaterialNotFoundException  the raw material not found exception
   */
  public Product create(ProductCreationDto productCreationDto)
      throws ProductAlreadyExistsException, RawMaterialNotFoundException {

    Product product = new Product();
    product.setCode(productCreationDto.code());
    product.setName(productCreationDto.name());
    product.setPrice(productCreationDto.price());

    List<ProductRawMaterial> list = new ArrayList<>();

    if (productCreationDto.rawMaterials() != null) {

      for (ProductRawMaterialCreationDto item : productCreationDto.rawMaterials()) {

        RawMaterial rawMaterial = rawMaterialService.findById(item.rawMaterialId());

        ProductRawMaterial productRawMaterial = new ProductRawMaterial(
            product,
            rawMaterial,
            item.requiredQuantity()
        );

        list.add(productRawMaterial);
      }
    }

    product.setRawMaterials(list);

    for (ProductRawMaterial productRawMaterial : list) {
      productRawMaterial.setProduct(product);
    }

    return productRepository.save(product);
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<Product> findAll() {
    return productRepository.findAllWithRawMaterials();
  }

  /**
   * Find by id product.
   *
   * @param id the id
   * @return the product
   * @throws ProductNotFoundException the product not found exception
   */
  public Product findById(Long id) throws ProductNotFoundException {
    return productRepository.findById(id)
        .orElseThrow(ProductNotFoundException::new);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @throws ProductNotFoundException the product not found exception
   */
  public void delete(Long id) throws ProductNotFoundException {
    Product existing = findById(id);
    productRepository.delete(existing);
  }

  /**
   * Update product.
   *
   * @param id                 the id
   * @param productCreationDto the product creation dto
   * @return the product
   * @throws ProductNotFoundException      the product not found exception
   * @throws ProductAlreadyExistsException the product already exists exception
   * @throws RawMaterialNotFoundException  the raw material not found exception
   */
  public Product update(Long id, ProductCreationDto productCreationDto)
      throws ProductNotFoundException, ProductAlreadyExistsException, RawMaterialNotFoundException {

    Product existing = findById(id);

    if (!existing.getCode().equals(productCreationDto.code()) && productRepository.existsByCode(
        productCreationDto.code())) {
      throw new ProductAlreadyExistsException();
    }

    existing.setCode(productCreationDto.code());
    existing.setName(productCreationDto.name());
    existing.setPrice(productCreationDto.price());

    if (existing.getRawMaterials() == null) {
      existing.setRawMaterials(new ArrayList<>());
    }

    existing.getRawMaterials().clear();

    if (productCreationDto.rawMaterials() != null) {

      for (ProductRawMaterialCreationDto productRawMaterialCreationDto : productCreationDto.rawMaterials()) {

        RawMaterial rawMaterial = rawMaterialService.findById(
            productRawMaterialCreationDto.rawMaterialId());

        ProductRawMaterial productRawMaterial = new ProductRawMaterial(
            existing,
            rawMaterial,
            productRawMaterialCreationDto.requiredQuantity()
        );

        productRawMaterial.setProduct(existing);

        existing.getRawMaterials().add(productRawMaterial);
      }
    }

    return productRepository.save(existing);
  }

  /**
   * Gets production suggestions.
   *
   * @return the production suggestions
   */
  public List<ProductionSuggestionDto> getProductionSuggestions() {

    List<Product> products = productRepository.findAllWithRawMaterials();

    products.sort(Comparator.comparing(Product::getPrice).reversed());

    Map<Long, BigDecimal> stockMap = new HashMap<>();

    for (Product product : products) {
      if (product.getRawMaterials() == null) {
        continue;
      }

      for (ProductRawMaterial prm : product.getRawMaterials()) {
        if (prm.getRawMaterial() == null) {
          continue;
        }

        RawMaterial rm = prm.getRawMaterial();

        stockMap.putIfAbsent(
            rm.getId(),
            rm.getStockQuantity() == null ? BigDecimal.ZERO : rm.getStockQuantity()
        );
      }
    }

    List<ProductionSuggestionDto> result = new ArrayList<>();

    // 3) Para cada produto, calcula quantas unidades dá pra fazer com o estoque ATUAL
    for (Product product : products) {

      if (product.getRawMaterials() == null || product.getRawMaterials().isEmpty()) {
        continue;
      }

      BigDecimal maxUnits = calculateMaxUnitsUsingStockMap(product, stockMap);

      if (maxUnits == null || maxUnits.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      // 4) Desconta estoque do produto produzido
      for (ProductRawMaterial prm : product.getRawMaterials()) {

        if (prm.getRawMaterial() == null || prm.getRequiredQuantity() == null) {
          continue;
        }

        Long rmId = prm.getRawMaterial().getId();
        BigDecimal required = prm.getRequiredQuantity();

        BigDecimal currentStock = stockMap.getOrDefault(rmId, BigDecimal.ZERO);

        BigDecimal used = required.multiply(maxUnits);

        BigDecimal newStock = currentStock.subtract(used);

        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
          newStock = BigDecimal.ZERO;
        }

        stockMap.put(rmId, newStock);
      }

      BigDecimal totalValue = maxUnits.multiply(product.getPrice());

      result.add(new ProductionSuggestionDto(
          product.getId(),
          product.getName(),
          product.getPrice(),
          maxUnits,
          totalValue
      ));
    }

    return result;
  }

  private BigDecimal calculateMaxUnitsUsingStockMap(
      Product product,
      Map<Long, BigDecimal> stockMap
  ) {

    BigDecimal maxUnits = null;

    for (ProductRawMaterial prm : product.getRawMaterials()) {

      if (prm.getRawMaterial() == null || prm.getRequiredQuantity() == null) {
        continue;
      }

      BigDecimal required = prm.getRequiredQuantity();

      if (required.compareTo(BigDecimal.ZERO) <= 0) {
        continue;
      }

      Long rmId = prm.getRawMaterial().getId();
      BigDecimal stock = stockMap.getOrDefault(rmId, BigDecimal.ZERO);

      BigDecimal possible = stock.divide(required, 0, RoundingMode.DOWN);

      if (maxUnits == null || possible.compareTo(maxUnits) < 0) {
        maxUnits = possible;
      }
    }

    return maxUnits;
  }

}