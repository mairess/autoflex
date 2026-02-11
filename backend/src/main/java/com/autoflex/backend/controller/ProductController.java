package com.autoflex.backend.controller;

import com.autoflex.backend.controller.dto.ProductCreationDto;
import com.autoflex.backend.controller.dto.ProductResponseDto;
import com.autoflex.backend.controller.dto.ProductionSuggestionDto;
import com.autoflex.backend.entity.Product;
import com.autoflex.backend.service.ProductService;
import com.autoflex.backend.service.exception.ProductAlreadyExistsException;
import com.autoflex.backend.service.exception.ProductNotFoundException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import jakarta.validation.Valid;
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

  /**
   * Instantiates a new Product controller.
   *
   * @param productService the product service
   */
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Create product response dto.
   *
   * @param productCreationDto the product creation dto
   * @return the product response dto
   * @throws ProductAlreadyExistsException the product already exists exception
   * @throws RawMaterialNotFoundException  the raw material not found exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponseDto create(@RequestBody @Valid ProductCreationDto productCreationDto)
      throws ProductAlreadyExistsException, RawMaterialNotFoundException {

    return ProductResponseDto.fromEntity(productService.create(productCreationDto));
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  @GetMapping
  public List<ProductResponseDto> findAll() {
    return productService.findAll().stream().map(ProductResponseDto::fromEntity).toList();
  }

  /**
   * Find by id product response dto.
   *
   * @param id the id
   * @return the product response dto
   * @throws ProductNotFoundException the product not found exception
   */
  @GetMapping("/{id}")
  public ProductResponseDto findById(@PathVariable Long id)
      throws ProductNotFoundException {
    return ProductResponseDto.fromEntity(productService.findById(id));
  }

  /**
   * Update product response dto.
   *
   * @param id                 the id
   * @param productCreationDto the product creation dto
   * @return the product response dto
   * @throws ProductNotFoundException      the product not found exception
   * @throws ProductAlreadyExistsException the product already exists exception
   * @throws RawMaterialNotFoundException  the raw material not found exception
   */
  @PutMapping("/{id}")
  public ProductResponseDto update(
      @PathVariable Long id,
      @RequestBody ProductCreationDto productCreationDto
  ) throws ProductNotFoundException, ProductAlreadyExistsException, RawMaterialNotFoundException {

    Product updated = productService.update(id, productCreationDto);
    return ProductResponseDto.fromEntity(updated);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @throws ProductNotFoundException the product not found exception
   */
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
    System.out.println("asdad");
    return productService.getProductionSuggestions();
  }
}