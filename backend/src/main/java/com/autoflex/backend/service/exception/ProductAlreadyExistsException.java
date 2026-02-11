package com.autoflex.backend.service.exception;

/**
 * The type Product already exists exception.
 */
public class ProductAlreadyExistsException extends Exception {

  /**
   * Instantiates a new Product already exists exception.
   *
   * @param message the message
   */
  public ProductAlreadyExistsException() {
    super("Product code already exists!");
  }

}