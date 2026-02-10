package com.autoflex.backend.controller;

import com.autoflex.backend.controller.dto.ProductCreationDto;
import com.autoflex.backend.controller.dto.ProductRawMaterialCreationDto;
import com.autoflex.backend.controller.dto.ProductResponseDto;
import com.autoflex.backend.controller.dto.ProductionSuggestionDto;
import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.service.ProductService;
import com.autoflex.backend.service.RawMaterialService;
import com.autoflex.backend.service.exception.ProductAlreadyExistsException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;
  private final RawMaterialService rawMaterialService;

  /**
   * Instantiates a new Product controller.
   *
   * @param productService     the product service
   * @param rawMaterialService the raw material service
   */
  @Autowired
  public ProductController(ProductService productService,
      RawMaterialService rawMaterialService) {
    this.productService = productService;
    this.rawMaterialService = rawMaterialService;
  }

  /**
   * Create product product response dto.
   *
   * @param productCreationDto the product creation dto
   * @return the product response dto
   * @throws ProductAlreadyExistsException the product already exists exception
   * @throws RawMaterialNotFoundException  the raw material not found exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponseDto createProduct(@RequestBody ProductCreationDto productCreationDto)
      throws ProductAlreadyExistsException, RawMaterialNotFoundException {
    Product product = new Product();
    product.setCode(productCreationDto.code());
    product.setName(productCreationDto.name());
    product.setPrice(productCreationDto.price());

    List<ProductRawMaterial> list = new ArrayList<>();

    for (ProductRawMaterialCreationDto productRawMaterialCreationDto : productCreationDto.rawMaterials()) {
      RawMaterial rawMaterial = rawMaterialService.findById(
          productRawMaterialCreationDto.rawMaterialId());

      ProductRawMaterial productRawMaterial = new ProductRawMaterial(
          product,
          rawMaterial,
          productRawMaterialCreationDto.requiredQuantity()
      );
      list.add(productRawMaterial);
    }
    product.setRawMaterials(list);
    return ProductResponseDto.fromEntity(
        productService.createProduct(product)
    );
  }

  /**
   * Suggestions list.
   *
   * @return the list
   */
  @GetMapping("/production-suggestions")
  public List<ProductionSuggestionDto> suggestions() {
    return productService.getProductionSuggestions();
  }
}