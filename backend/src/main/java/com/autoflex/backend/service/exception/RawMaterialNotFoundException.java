package com.autoflex.backend.service.exception;

public class RawMaterialNotFoundException extends NotFoundException {

  public RawMaterialNotFoundException() {
    super("Raw material not found!");
  }
}