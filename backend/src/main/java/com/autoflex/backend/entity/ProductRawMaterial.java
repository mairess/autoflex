package com.autoflex.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * The type Product raw material.
 */
@Entity
@Table(name = "product_raw_materials")
public class ProductRawMaterial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "raw_material_id")
  private RawMaterial rawMaterial;

  private BigDecimal requiredQuantity;

  private ProductRawMaterial() {
  }

  /**
   * Instantiates a new Product raw material.
   *
   * @param product          the product
   * @param rawMaterial      the raw material
   * @param requiredQuantity the required quantity
   */
  public ProductRawMaterial(Product product, RawMaterial rawMaterial,
      BigDecimal requiredQuantity) {
    this.product = product;
    this.rawMaterial = rawMaterial;
    this.requiredQuantity = requiredQuantity;
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
   * Gets product.
   *
   * @return the product
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets product.
   *
   * @param product the product
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Gets raw material.
   *
   * @return the raw material
   */
  public RawMaterial getRawMaterial() {
    return rawMaterial;
  }

  /**
   * Sets raw material.
   *
   * @param rawMaterial the raw material
   */
  public void setRawMaterial(RawMaterial rawMaterial) {
    this.rawMaterial = rawMaterial;
  }

  /**
   * Gets required quantity.
   *
   * @return the required quantity
   */
  public BigDecimal getRequiredQuantity() {
    return requiredQuantity;
  }

  /**
   * Sets required quantity.
   *
   * @param requiredQuantity the required quantity
   */
  public void setRequiredQuantity(BigDecimal requiredQuantity) {
    this.requiredQuantity = requiredQuantity;
  }
}