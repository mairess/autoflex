package com.autoflex.backend.service.exception;

/**
 * The type Raw material in use exception.
 */
public class RawMaterialInUseException extends Exception {

  /**
   * Instantiates a new Raw material in use exception.
   */
  public RawMaterialInUseException() {
    super("Raw material is being used and cannot be deleted!");
  }
}