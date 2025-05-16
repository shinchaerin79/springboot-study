package com.likelion.springbootstudy.domain.book.entity;

import com.likelion.springbootstudy.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;   // 제목

  @Column(nullable = false)
  private String author;  // 저자

  @Column(nullable = false)
  private String price;   // 가격

  @Column(nullable = false)
  private String publisher; // 출판사

  public void setTitle(String title) {
    this.title = title;
  }
  public void setAuthor(String author) {
    this.author = author;
  }
  public void setPrice(String price) {
    this.price = price;
  }
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

}

