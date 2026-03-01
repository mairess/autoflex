package com.autoflex.backend.service.exception;

/**
 * The type Raw material already exists exception.
 */
public class RawMaterialAlreadyExistsException extends Exception {

  /**
   * Instantiates a new Raw material already exists exception.
   */
  public RawMaterialAlreadyExistsException() {
    super("Raw material code already exists!");
  }
}