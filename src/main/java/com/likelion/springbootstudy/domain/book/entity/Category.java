package com.likelion.springbootstudy.domain.book.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "책 카테고리")
public enum Category {
  @Schema(description = "소설") NOVEL,
  @Schema(description = "공포") HORROR,
  @Schema(description = "로맨스") ROMANCE,
  @Schema(description = "스릴러") THRILLER,
  @Schema(description = "판타지") FANTASY,
  @Schema(description = "공상과학") SCIENCE_FICTION
}