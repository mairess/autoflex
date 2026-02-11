package com.autoflex.backend.repository;

import com.autoflex.backend.entity.ProductRawMaterial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Product raw material repository.
 */
@Repository
public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

  /**
   * Find by product id list.
   *
   * @param productId the product id
   * @return the list
   */
  List<ProductRawMaterial> findByProductId(Long productId);

  /**
   * Delete by product id and raw material id.
   *
   * @param productId     the product id
   * @param rawMaterialId the raw material id
   */
  void deleteByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);
}