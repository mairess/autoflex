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
@DisplayName("RawMaterial integration tests")
class RawMaterialIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  RawMaterialRepository rawMaterialRepository;

  @Autowired
  ProductRepository productRepository;

  @BeforeEach
  void clean() {
    productRepository.deleteAll();
    rawMaterialRepository.deleteAll();
  }

  @Test
  @DisplayName("Should return empty list")
  void shouldReturnEmptyList() throws Exception {
    mockMvc.perform(get("/raw-materials"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should create raw material")
  void shouldCreateRawMaterial() throws Exception {

    String json = """
        {
          "code": "RM-01",
          "name": "Steel",
          "stockQuantity": 100
        }
        """;

    mockMvc.perform(post("/raw-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code").value("RM-01"))
        .andExpect(jsonPath("$.name").value("Steel"))
        .andExpect(jsonPath("$.stockQuantity").value(100));
  }

  @Test
  @DisplayName("Should not allow duplicate code on create")
  void shouldNotAllowDuplicateCodeOnCreate() throws Exception {

    RawMaterial existing = new RawMaterial();
    existing.setCode("RM-01");
    existing.setName("Steel");
    existing.setStockQuantity(BigDecimal.valueOf(100));
    rawMaterialRepository.save(existing);

    String json = """
        {
          "code": "RM-01",
          "name": "Iron",
          "stockQuantity": 50
        }
        """;

    mockMvc.perform(post("/raw-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Should update raw material")
  void shouldUpdateRawMaterial() throws Exception {

    RawMaterial material = new RawMaterial();
    material.setCode("RM-01");
    material.setName("Steel");
    material.setStockQuantity(BigDecimal.valueOf(100));
    material = rawMaterialRepository.save(material);

    String updateJson = """
        {
          "code": "RM-02",
          "name": "Iron",
          "stockQuantity": 150
        }
        """;

    mockMvc.perform(put("/raw-materials/" + material.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("RM-02"))
        .andExpect(jsonPath("$.name").value("Iron"))
        .andExpect(jsonPath("$.stockQuantity").value(150));
  }

  @Test
  @DisplayName("Should not allow duplicate code on update")
  void shouldNotAllowDuplicateCodeOnUpdate() throws Exception {

    RawMaterial m1 = new RawMaterial();
    m1.setCode("RM-01");
    m1.setName("Steel");
    m1.setStockQuantity(BigDecimal.valueOf(100));
    m1 = rawMaterialRepository.save(m1);

    RawMaterial m2 = new RawMaterial();
    m2.setCode("RM-02");
    m2.setName("Iron");
    m2.setStockQuantity(BigDecimal.valueOf(50));
    m2 = rawMaterialRepository.save(m2);

    String updateJson = """
        {
          "code": "RM-01",
          "name": "Copper",
          "stockQuantity": 30
        }
        """;

    mockMvc.perform(put("/raw-materials/" + m2.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateJson))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Should list raw materials")
  void shouldListRawMaterials() throws Exception {

    RawMaterial material = new RawMaterial();
    material.setCode("RM-01");
    material.setName("Steel");
    material.setStockQuantity(BigDecimal.valueOf(100));
    rawMaterialRepository.save(material);

    mockMvc.perform(get("/raw-materials"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].name").value("Steel"));
  }

  @Test
  @DisplayName("Should delete raw material")
  void shouldDeleteRawMaterial() throws Exception {

    RawMaterial material = new RawMaterial();
    material.setCode("RM-01");
    material.setName("Steel");
    material.setStockQuantity(BigDecimal.valueOf(100));
    material = rawMaterialRepository.save(material);

    mockMvc.perform(delete("/raw-materials/" + material.getId()))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/raw-materials"))
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  @DisplayName("Should not delete raw material in use")
  void shouldNotDeleteRawMaterialInUse() throws Exception {

    RawMaterial steel = new RawMaterial();
    steel.setCode("RM-01");
    steel.setName("Steel");
    steel.setStockQuantity(BigDecimal.valueOf(100));
    steel = rawMaterialRepository.save(steel);

    Product product = new Product();
    product.setCode("CAR-01");
    product.setName("Car");
    product.setPrice(BigDecimal.valueOf(1000));

    ProductRawMaterial prm = new ProductRawMaterial(product, steel, BigDecimal.valueOf(10));
    product.setRawMaterials(List.of(prm));
    productRepository.save(product);

    mockMvc.perform(delete("/raw-materials/" + steel.getId()))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Should fail creation with missing required fields")
  void shouldFailCreationWithMissingFields() throws Exception {

    String json = """
        {
          "name": "",
          "stockQuantity": null
        }
        """;

    mockMvc.perform(post("/raw-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());
  }
}