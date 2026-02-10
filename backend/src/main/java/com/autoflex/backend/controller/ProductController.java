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
import com.autoflex.backend.service.exception.ProductNotFoundException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ProductResponseDto create(@RequestBody ProductCreationDto productCreationDto)
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
        productService.create(product)
    );
  }

  @GetMapping
  public List<ProductResponseDto> findAll() {
    return productService.findAll().stream().map(ProductResponseDto::fromEntity).toList();
  }

  @GetMapping("/{id}")
  public ProductResponseDto findById(@PathVariable Long id)
      throws ProductNotFoundException {
    return ProductResponseDto.fromEntity(productService.findById(id));
  }

  @PutMapping("/{id}")
  public ProductResponseDto update(
      @PathVariable Long id,
      @RequestBody ProductCreationDto dto
  ) throws ProductNotFoundException, ProductAlreadyExistsException, RawMaterialNotFoundException {

    Product updated = productService.update(id, dto);
    return ProductResponseDto.fromEntity(updated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ProductNotFoundException {
    productService.delete(id);
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