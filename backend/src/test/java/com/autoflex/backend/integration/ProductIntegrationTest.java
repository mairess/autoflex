package com.autoflex.backend.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.repository.ProductRepository;
import com.autoflex.backend.repository.RawMaterialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Product integration tests")
class ProductIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  RawMaterialRepository rawMaterialRepository;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void clean() {
    productRepository.deleteAll();
    rawMaterialRepository.deleteAll();
  }

  @Test
  @DisplayName("Should return empty list")
  void shouldReturnEmptyList() throws Exception {
    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should create product")
  void shouldCreateProduct() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    String json = """
        {
          "code": "CAR-01",
          "name": "Car",
          "price": 50000,
          "rawMaterials": [
            {"rawMaterialId": %d, "requiredQuantity": 10}
          ]
        }
        """.formatted(steel.getId());

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Car"))
        .andExpect(jsonPath("$.code").value("CAR-01"));
  }

  @Test
  @DisplayName("Should update product")
  void shouldUpdateProduct() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(200));
    steel = rawMaterialRepository.save(steel);

    Product product = new Product();
    product.setName("Car");
    product.setCode("CAR-01");
    product.setPrice(BigDecimal.valueOf(50000));
    product = productRepository.save(product);

    String updateJson = """
        {
          "code": "CAR-UPDATED",
          "name": "Car Deluxe",
          "price": 60000,
          "rawMaterials": [
            {"rawMaterialId": %d, "requiredQuantity": 20}
          ]
        }
        """.formatted(steel.getId());

    mockMvc.perform(put("/products/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("CAR-UPDATED"))
        .andExpect(jsonPath("$.name").value("Car Deluxe"))
        .andExpect(jsonPath("$.price").value(60000));
  }

  @Test
  @DisplayName("Should not allow duplicate code on create")
  void shouldNotAllowDuplicateCodeOnCreate() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    Product existing = new Product();
    existing.setName("Car");
    existing.setCode("CAR-01");
    existing.setPrice(BigDecimal.valueOf(1000));
    productRepository.save(existing);

    String json = """
        {
          "code": "CAR-01",
          "name": "Another Car",
          "price": 2000,
          "rawMaterials": [{"rawMaterialId": %d, "requiredQuantity": 5}]
        }
        """.formatted(steel.getId());

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Should not allow duplicate code on update")
  void shouldNotAllowDuplicateCodeOnUpdate() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    Product p1 = new Product();
    p1.setName("Car");
    p1.setCode("CAR-01");
    p1.setPrice(BigDecimal.valueOf(1000));
    p1 = productRepository.save(p1);

    Product p2 = new Product();
    p2.setName("Bike");
    p2.setCode("BIKE-01");
    p2.setPrice(BigDecimal.valueOf(500));
    p2 = productRepository.save(p2);

    String updateJson = """
        {
          "code": "CAR-01",
          "name": "Bike Updated",
          "price": 600,
          "rawMaterials": [{"rawMaterialId": %d, "requiredQuantity": 5}]
        }
        """.formatted(steel.getId());

    mockMvc.perform(put("/products/" + p2.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateJson))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Should list products")
  void shouldListProducts() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    Product product = new Product();
    product.setName("Car");
    product.setCode("CAR-01");
    product.setPrice(BigDecimal.valueOf(50000));

    ProductRawMaterial prm =
        new ProductRawMaterial(product, steel, BigDecimal.valueOf(10));

    product.setRawMaterials(List.of(prm));
    productRepository.save(product);

    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("Car"));
  }

  @Test
  @DisplayName("Should delete product")
  void shouldDeleteProduct() throws Exception {

    Product product = new Product();
    product.setName("Bike");
    product.setCode("BIKE-01");
    product.setPrice(BigDecimal.valueOf(1000));
    product = productRepository.save(product);

    mockMvc.perform(delete("/products/" + product.getId()))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/products"))
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should return production suggestions")
  void shouldReturnProductionSuggestions() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    Product product = new Product();
    product.setName("Car");
    product.setCode("CAR-01");
    product.setPrice(BigDecimal.valueOf(100));

    ProductRawMaterial prm =
        new ProductRawMaterial(product, steel, BigDecimal.valueOf(10));

    product.setRawMaterials(List.of(prm));
    productRepository.save(product);

    mockMvc.perform(get("/products/production-suggestions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].productName").value("Car"))
        .andExpect(jsonPath("$[0].quantityProduced").value(10))
        .andExpect(jsonPath("$[0].totalValue").value(1000.00));
  }

  @Test
  @DisplayName("Should ignore products without raw materials in suggestions")
  void shouldIgnoreProductsWithoutRawMaterials() throws Exception {

    Product product = new Product();
    product.setName("Empty Product");
    product.setCode("EMPTY-01");
    product.setPrice(BigDecimal.valueOf(1000));
    productRepository.save(product);

    mockMvc.perform(get("/products/production-suggestions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should ignore products with insufficient stock in suggestions")
  void shouldIgnoreProductsWithInsufficientStock() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.ZERO);
    steel = rawMaterialRepository.save(steel);

    Product product = new Product();
    product.setName("Car");
    product.setCode("CAR-01");
    product.setPrice(BigDecimal.valueOf(100));

    ProductRawMaterial prm =
        new ProductRawMaterial(product, steel, BigDecimal.valueOf(10));

    product.setRawMaterials(List.of(prm));
    productRepository.save(product);

    mockMvc.perform(get("/products/production-suggestions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should fail creation with missing required fields")
  void shouldFailCreationWithMissingFields() throws Exception {

    String json = """
        {
          "name": "",
          "price": null
        }
        """;

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());
  }
}