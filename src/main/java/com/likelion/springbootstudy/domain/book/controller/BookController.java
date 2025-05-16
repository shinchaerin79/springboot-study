package com.likelion.springbootstudy.domain.book.controller;

import com.likelion.springbootstudy.domain.book.dto.request.BookRequest;
import com.likelion.springbootstudy.domain.book.entity.Book;
import com.likelion.springbootstudy.domain.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "책 관련 API")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @Operation(summary = "책 정보 등록", description = "책 정보를 등록하는 API")
  @PostMapping
  public ResponseEntity<String> createBook(@RequestBody BookRequest request) {
    bookService.saveBook(request);
    return ResponseEntity.ok("책 정보 등록 성공: " + request.getTitle());
  }
  @Operation(summary = "특정 ID의 책 정보 조회", description = "특정 ID의 책 정보를 조회하는 API")
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBook(@PathVariable Long id) {
    Book book = bookService.getBook(id);
    return ResponseEntity.ok(book);
  }

  @Operation(summary = "책 정보 수정", description = "책 정보를 수정하는 API")
  @PutMapping("/{id}")
  public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
    bookService.updateBook(id, request);
    return ResponseEntity.ok("책 정보 수정 성공 - ID: " + id);
  }

  @Operation(summary = "특정 ID의 책 정보 삭제", description = "특정 ID의 책 정보를 삭제하는 API")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.ok("책 정보 삭제 성공 - ID: " + id);
  }
}
