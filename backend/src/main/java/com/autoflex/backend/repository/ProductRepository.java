package com.autoflex.backend.repository;

import com.autoflex.backend.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Product repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  /**
   * Exists by code boolean.
   *
   * @param code the code
   * @return the boolean
   */
  boolean existsByCode(String code);

  /**
   * Find all with raw materials list.
   *
   * @return the list
   */
  @Query("""
        select distinct p
        from Product p
        left join fetch p.rawMaterials prm
      """)
  List<Product> findAllWithRawMaterials();

}