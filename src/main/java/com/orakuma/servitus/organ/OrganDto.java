package com.orakuma.servitus.organ;

public record OrganDto(Long id, String name, String description, String content, String publicId) {

  /** Used by JPQL constructor expressions; {@code content} is not stored on the entity. */
  public OrganDto(Long id, String name, String description, String publicId) {
    this(id, name, description, null, publicId);
  }
}
