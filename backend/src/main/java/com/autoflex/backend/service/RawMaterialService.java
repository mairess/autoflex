package com.autoflex.backend.service;

import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.repository.RawMaterialRepository;
import com.autoflex.backend.service.exception.RawMaterialAlreadyExistsException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The type Raw material service.
 */
@Service
public class RawMaterialService {

  private final RawMaterialRepository rawMaterialRepository;

  /**
   * Instantiates a new Raw material service.
   *
   * @param rawMaterialRepository the raw material repository
   */
  public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
    this.rawMaterialRepository = rawMaterialRepository;
  }

  /**
   * Create raw material.
   *
   * @param rawMaterial the raw material
   * @return the raw material
   * @throws RawMaterialAlreadyExistsException the raw material already exists exception
   */
  public RawMaterial create(RawMaterial rawMaterial) throws RawMaterialAlreadyExistsException {

    if (rawMaterialRepository.existsByCode(rawMaterial.getCode())) {
      throw new RawMaterialAlreadyExistsException();
    }

    return rawMaterialRepository.save(rawMaterial);
  }

  /**
   * Find by id raw material.
   *
   * @param id the id
   * @return the raw material
   * @throws RawMaterialNotFoundException the raw material not found exception
   */
  public RawMaterial findById(Long id) throws RawMaterialNotFoundException {
    return rawMaterialRepository.findById(id).orElseThrow(RawMaterialNotFoundException::new);
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<RawMaterial> findAll() {
    return rawMaterialRepository.findAll();
  }

  /**
   * Update raw material.
   *
   * @param id   the id
   * @param data the data
   * @return the raw material
   * @throws RawMaterialNotFoundException the raw material not found exception
   */
  public RawMaterial update(Long id, RawMaterial data)
      throws RawMaterialNotFoundException, RawMaterialAlreadyExistsException {

    RawMaterial existing = this.findById(id);

    if (!existing.getCode().equals(data.getCode()) && rawMaterialRepository.existsByCode(
        data.getCode())) {
      throw new RawMaterialAlreadyExistsException();
    }

    existing.setCode(data.getCode());
    existing.setName(data.getName());
    existing.setStockQuantity(data.getStockQuantity());

    return rawMaterialRepository.save(existing);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @throws RawMaterialNotFoundException the raw material not found exception
   */
  public void delete(Long id) throws RawMaterialNotFoundException {
    RawMaterial existing = this.findById(id);
    rawMaterialRepository.delete(existing);
  }


}