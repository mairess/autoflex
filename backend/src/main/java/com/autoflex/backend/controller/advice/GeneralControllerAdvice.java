package com.autoflex.backend.controller.advice;

import com.autoflex.backend.exceptions.ValidationErrorResponse;
import com.autoflex.backend.service.exception.NotFoundException;
import com.autoflex.backend.service.exception.ProductAlreadyExistsException;
import com.autoflex.backend.service.exception.RawMaterialAlreadyExistsException;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type General controller advice.
 */
@ControllerAdvice
public class GeneralControllerAdvice {

  /**
   * Handle validation errors response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
      MethodArgumentNotValidException exception) {

    List<String> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();

    ValidationErrorResponse response = new ValidationErrorResponse(errors);

    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Handle not found response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  /**
   * Handle product already exists response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(ProductAlreadyExistsException.class)
  public ResponseEntity<String> handleProductAlreadyExists(
      ProductAlreadyExistsException exception) {

    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
  }

  /**
   * Handle raw material already exists response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(RawMaterialAlreadyExistsException.class)
  public ResponseEntity<String> handleRawMaterialAlreadyExists(
      RawMaterialAlreadyExistsException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
  }
}