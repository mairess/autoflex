package com.autoflex.backend.service.exception;

public class ProductNotFoundException extends NotFoundException {

  public ProductNotFoundException() {
    super("Product not found!");
  }
}