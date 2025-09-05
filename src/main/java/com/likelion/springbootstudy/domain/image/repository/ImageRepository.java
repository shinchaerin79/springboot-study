package com.likelion.springbootstudy.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.springbootstudy.domain.image.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {}
