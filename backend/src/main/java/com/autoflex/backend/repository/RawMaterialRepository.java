package com.autoflex.backend.repository;

import com.autoflex.backend.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Raw material repository.
 */
@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

  /**
   * Exists by code boolean.
   *
   * @param code the code
   * @return the boolean
   */
  boolean existsByCode(String code);

}