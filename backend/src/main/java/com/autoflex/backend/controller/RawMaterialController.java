package com.autoflex.backend.controller;

import com.autoflex.backend.controller.dto.RawMaterialCreationDto;
import com.autoflex.backend.controller.dto.RawMaterialResponseDto;
import com.autoflex.backend.entity.RawMaterial;
import com.autoflex.backend.service.RawMaterialService;
import com.autoflex.backend.service.exception.RawMaterialAlreadyExistsException;
import com.autoflex.backend.service.exception.RawMaterialNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Raw material controller.
 */
@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

  private final RawMaterialService rawMaterialService;

  /**
   * Instantiates a new Raw material controller.
   *
   * @param rawMaterialService the raw material service
   */
  public RawMaterialController(RawMaterialService rawMaterialService) {
    this.rawMaterialService = rawMaterialService;
  }

  /**
   * Create raw material response dto.
   *
   * @param rawMaterialCreationDto the raw material creation dto
   * @return the raw material response dto
   * @throws RawMaterialAlreadyExistsException the raw material already exists exception
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RawMaterialResponseDto create(
      @RequestBody @Valid RawMaterialCreationDto rawMaterialCreationDto)
      throws RawMaterialAlreadyExistsException {
    RawMaterial saved = rawMaterialService.create(rawMaterialCreationDto.toEntity());
    return RawMaterialResponseDto.fromEntity(saved);
  }

  /**
   * Find by id raw material response dto.
   *
   * @param id the id
   * @return the raw material response dto
   * @throws RawMaterialNotFoundException the raw material not found exception
   */
  @GetMapping("/{id}")
  public RawMaterialResponseDto findById(
      @PathVariable Long id
  ) throws RawMaterialNotFoundException {
    return RawMaterialResponseDto.fromEntity(
        rawMaterialService.findById(id)
    );
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  @GetMapping
  public List<RawMaterialResponseDto> findAll() {

    return rawMaterialService.findAll()
        .stream()
        .map(RawMaterialResponseDto::fromEntity)
        .toList();
  }

  /**
   * Update raw material response dto.
   *
   * @param id  the id
   * @param dto the dto
   * @return the raw material response dto
   * @throws RawMaterialNotFoundException      the raw material not found exception
   * @throws RawMaterialAlreadyExistsException the raw material already exists exception
   */
  @PutMapping("/{id}")
  public RawMaterialResponseDto update(
      @PathVariable Long id,
      @RequestBody RawMaterialCreationDto dto
  ) throws RawMaterialNotFoundException, RawMaterialAlreadyExistsException {
    RawMaterial updated =
        rawMaterialService.update(id, dto.toEntity());
    return RawMaterialResponseDto.fromEntity(updated);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @throws RawMaterialNotFoundException the raw material not found exception
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable Long id
  ) throws RawMaterialNotFoundException {
    rawMaterialService.delete(id);
  }
}