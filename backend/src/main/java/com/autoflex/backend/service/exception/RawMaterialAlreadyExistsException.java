package com.autoflex.backend.service.exception;

public class RawMaterialAlreadyExistsException extends Exception {

  public RawMaterialAlreadyExistsException() {
    super("Raw material code already exists!");
  }
}