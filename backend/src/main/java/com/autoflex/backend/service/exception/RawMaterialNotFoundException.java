package com.autoflex.backend.service.exception;

/**
 * The type Raw material not found exception.
 */
public class RawMaterialNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Raw material not found exception.
   */
  public RawMaterialNotFoundException() {
    super("Raw material not found!");
  }
}