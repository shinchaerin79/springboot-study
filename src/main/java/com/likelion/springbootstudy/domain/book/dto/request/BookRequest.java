package com.likelion.springbootstudy.domain.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
  private String title;
  private String author;
  private String price;
  private String publisher;

  public void setTitle(String title) { this.title = title; }
  public void setAuthor(String author) { this.author = author; }
  public void setPrice(String price) { this.price = price; }
  public void setPublisher(String publisher) { this.publisher = publisher; }
}