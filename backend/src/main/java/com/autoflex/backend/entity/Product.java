package com.autoflex.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Product.
 */
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String code;
  private String name;
  private BigDecimal price;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<ProductRawMaterial> rawMaterials;

  /**
   * Instantiates a new Product.
   */
  public Product() {

  }

  /**
   * Instantiates a new Product.
   *
   * @param code         the code
   * @param name         the name
   * @param price        the price
   * @param rawMaterials the raw materials
   */
  public Product(String code, String name, BigDecimal price,
      List<ProductRawMaterial> rawMaterials) {
    this.code = code;
    this.name = name;
    this.price = price;
    this.rawMaterials = rawMaterials;
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
   * Gets price.
   *
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * Gets raw materials.
   *
   * @return the raw materials
   */
  public List<ProductRawMaterial> getRawMaterials() {
    return rawMaterials;
  }

  /**
   * Sets raw materials.
   *
   * @param rawMaterials the raw materials
   */
  public void setRawMaterials(List<ProductRawMaterial> rawMaterials) {
    this.rawMaterials = rawMaterials;
  }
}