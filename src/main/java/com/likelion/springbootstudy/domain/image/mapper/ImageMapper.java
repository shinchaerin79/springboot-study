package com.likelion.springbootstudy.domain.image.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.likelion.springbootstudy.domain.image.dto.response.ImageResponse;
import com.likelion.springbootstudy.domain.image.entity.Image;

@Component
public class ImageMapper {

  public ImageResponse toImageResponse(List<Image> images) {
    return ImageResponse.builder()
        .imagesUrl(images.stream().map(Image::getImageUrl).toList())
        .build();
  }
}
