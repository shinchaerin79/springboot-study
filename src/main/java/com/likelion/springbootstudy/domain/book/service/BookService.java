package com.likelion.springbootstudy.domain.book.service;

import com.likelion.springbootstudy.domain.book.dto.request.BookRequest;
import com.likelion.springbootstudy.domain.book.entity.Book;
import com.likelion.springbootstudy.domain.book.repository.BookRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  @Transactional
  public void saveBook(BookRequest request) {
    Book book = Book.builder()
        .title(request.getTitle())
        .author(request.getAuthor())
        .price(request.getPrice())
        .publisher(request.getPublisher())
        .build();

    bookRepository.save(book);
    log.info("Book saved: {}", book.getTitle());
  }

  @Transactional(readOnly = true)
  public Book getBook(Long id) {
    return bookRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("해당 ID의 책을 찾을 수 없습니다: " + id));
  }

  @Transactional
  public void deleteBook(Long id) {
    bookRepository.deleteById(id);
    log.info("Book deleted - ID: {}", id);
  }

  @Transactional
  public void updateBook(Long id, BookRequest request) {
    Book book = getBook(id); // 기존 엔티티 가져오기

    book.setTitle(request.getTitle());
    book.setAuthor(request.getAuthor());
    book.setPrice(request.getPrice());
    book.setPublisher(request.getPublisher());

    log.info("Book updated: {}", book.getId());
  }

}
