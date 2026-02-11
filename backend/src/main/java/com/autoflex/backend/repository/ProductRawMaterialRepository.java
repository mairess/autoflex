package com.autoflex.backend.repository;

import com.autoflex.backend.entity.ProductRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Product raw material repository.
 */
@Repository
public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

}