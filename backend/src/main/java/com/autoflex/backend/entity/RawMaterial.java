package com.autoflex.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * The type Raw material.
 */
@Entity
@Table(name = "raw_materials")
public class RawMaterial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private String name;
  private BigDecimal stockQuantity;

  /**
   * Instantiates a new Raw material.
   */
  public RawMaterial() {

  }

  /**
   * Instantiates a new Raw material.
   *
   * @param code          the code
   * @param name          the name
   * @param stockQuantity the stock quantity
   */
  public RawMaterial(String code, String name, BigDecimal stockQuantity) {
    this.code = code;
    this.name = name;
    this.stockQuantity = stockQuantity;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets stock quantity.
   *
   * @return the stock quantity
   */
  public BigDecimal getStockQuantity() {
    return stockQuantity;
  }

  /**
   * Sets stock quantity.
   *
   * @param stockQuantity the stock quantity
   */
  public void setStockQuantity(BigDecimal stockQuantity) {
    this.stockQuantity = stockQuantity;
  }
}