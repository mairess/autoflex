package com.autoflex.backend.service.exception;

/**
 * The type Product not found exception.
 */
public class ProductNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Product not found exception.
   */
  public ProductNotFoundException() {
    super("Product not found!");
  }
}