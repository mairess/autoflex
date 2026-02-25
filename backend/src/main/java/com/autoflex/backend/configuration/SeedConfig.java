package com.autoflex.backend.configuration;

import com.autoflex.backend.entity.Product;
import com.autoflex.backend.entity.ProductRawMaterial;
import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.repository.ProductRawMaterialRepository;
import com.autoflex.backend.repository.ProductRepository;
import com.autoflex.backend.repository.RawMaterialRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig implements CommandLineRunner {

  private final ProductRawMaterialRepository productRawMaterialRepository;
  private final RawMaterialRepository rawMaterialRepository;
  private final ProductRepository productRepository;

  public SeedConfig(ProductRawMaterialRepository productRawMaterialRepository,
      RawMaterialRepository rawMaterialRepository, ProductRepository productRepository) {
    this.productRawMaterialRepository = productRawMaterialRepository;
    this.rawMaterialRepository = rawMaterialRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (rawMaterialRepository.count() > 0) {
      return;
    }

    RawMaterial steel = new RawMaterial();
    steel.setCode("RM001");
    steel.setName("High-Strength Steel");
    steel.setStockQuantity(BigDecimal.valueOf(500.00));
    steel = rawMaterialRepository.save(steel);

    RawMaterial aluminum = new RawMaterial();
    aluminum.setCode("RM002");
    aluminum.setName("Aluminum Alloy");
    aluminum.setStockQuantity(BigDecimal.valueOf(300.00));
    aluminum = rawMaterialRepository.save(aluminum);

    RawMaterial castIron = new RawMaterial();
    castIron.setCode("RM003");
    castIron.setName("Cast Iron");
    castIron.setStockQuantity(BigDecimal.valueOf(400.00));
    castIron = rawMaterialRepository.save(castIron);

    RawMaterial rubber = new RawMaterial();
    rubber.setCode("RM004");
    rubber.setName("Rubber Compound");
    rubber.setStockQuantity(BigDecimal.valueOf(200.00));
    rubber = rawMaterialRepository.save(rubber);

    RawMaterial absPlastic = new RawMaterial();
    absPlastic.setCode("RM005");
    absPlastic.setName("ABS Plastic");
    absPlastic.setStockQuantity(BigDecimal.valueOf(350.00));
    absPlastic = rawMaterialRepository.save(absPlastic);

    Product brakeDisc = new Product();
    brakeDisc.setCode("PROD001");
    brakeDisc.setName("Front Brake Disc");
    brakeDisc.setPrice(BigDecimal.valueOf(120.00));
    brakeDisc = productRepository.save(brakeDisc);

    Product controlArm = new Product();
    controlArm.setCode("PROD002");
    controlArm.setName("Suspension Control Arm");
    controlArm.setPrice(BigDecimal.valueOf(180.00));
    controlArm = productRepository.save(controlArm);

    Product engineMount = new Product();
    engineMount.setCode("PROD003");
    engineMount.setName("Engine Mount");
    engineMount.setPrice(BigDecimal.valueOf(95.00));
    engineMount = productRepository.save(engineMount);

    Product bumperReinforcement = new Product();
    bumperReinforcement.setCode("PROD004");
    bumperReinforcement.setName("Front Bumper Reinforcement");
    bumperReinforcement.setPrice(BigDecimal.valueOf(210.00));
    bumperReinforcement = productRepository.save(bumperReinforcement);

    ProductRawMaterial prm1 = new ProductRawMaterial();
    prm1.setProduct(brakeDisc);
    prm1.setRawMaterial(castIron);
    prm1.setRequiredQuantity(BigDecimal.valueOf(15.00));
    productRawMaterialRepository.save(prm1);

    ProductRawMaterial prm2 = new ProductRawMaterial();
    prm2.setProduct(brakeDisc);
    prm2.setRawMaterial(steel);
    prm2.setRequiredQuantity(BigDecimal.valueOf(5.00));
    productRawMaterialRepository.save(prm2);

    ProductRawMaterial prm3 = new ProductRawMaterial();
    prm3.setProduct(controlArm);
    prm3.setRawMaterial(steel);
    prm3.setRequiredQuantity(BigDecimal.valueOf(12.00));
    productRawMaterialRepository.save(prm3);

    ProductRawMaterial prm4 = new ProductRawMaterial();
    prm4.setProduct(controlArm);
    prm4.setRawMaterial(rubber);
    prm4.setRequiredQuantity(BigDecimal.valueOf(4.00));
    productRawMaterialRepository.save(prm4);

    ProductRawMaterial prm5 = new ProductRawMaterial();
    prm5.setProduct(engineMount);
    prm5.setRawMaterial(castIron);
    prm5.setRequiredQuantity(BigDecimal.valueOf(8.00));
    productRawMaterialRepository.save(prm5);

    ProductRawMaterial prm6 = new ProductRawMaterial();
    prm6.setProduct(engineMount);
    prm6.setRawMaterial(rubber);
    prm6.setRequiredQuantity(BigDecimal.valueOf(6.00));
    productRawMaterialRepository.save(prm6);

    ProductRawMaterial prm7 = new ProductRawMaterial();
    prm7.setProduct(bumperReinforcement);
    prm7.setRawMaterial(aluminum);
    prm7.setRequiredQuantity(BigDecimal.valueOf(10.00));
    productRawMaterialRepository.save(prm7);

    ProductRawMaterial prm8 = new ProductRawMaterial();
    prm8.setProduct(bumperReinforcement);
    prm8.setRawMaterial(absPlastic);
    prm8.setRequiredQuantity(BigDecimal.valueOf(7.00));
    productRawMaterialRepository.save(prm8);

  }
}