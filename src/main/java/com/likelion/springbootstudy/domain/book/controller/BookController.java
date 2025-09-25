package com.likelion.springbootstudy.domain.book.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.likelion.springbootstudy.domain.book.dto.request.BookRequest;
import com.likelion.springbootstudy.domain.book.dto.response.BookResponse;
import com.likelion.springbootstudy.domain.book.entity.Category;
import com.likelion.springbootstudy.domain.book.service.BookService;
import com.likelion.springbootstudy.global.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Tag(name = "Book", description = "책 관련 API")
public class BookController {

  private final BookService bookService;

  @Operation(summary = "새 책 등록", description = "새로운 책을 등록하고, 등록된 책 정보를 반환합니다.")
  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BaseResponse<BookResponse>> createBook(
      @RequestPart(value = "imageList", required = false) List<MultipartFile> imageList,
      @RequestPart("book") @Valid BookRequest request,
      @RequestParam("categoryList") List<Category> categoryList) {
    BookResponse response = bookService.createBook(request, categoryList, imageList);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.success("책 생성에 성공하였습니다.", response));
  }

  @Operation(summary = "등록된 책 전체 조회", description = "등록된 모든 책 정보를 조회합니다.")
  @GetMapping(value = "")
  public ResponseEntity<BaseResponse<List<BookResponse>>> getAllBooks() {
    List<BookResponse> responseList = bookService.getAllBooks();
    return ResponseEntity.ok(BaseResponse.success("책 전체 조회에 성공했습니다.", responseList));
  }

  @Operation(summary = "등록된 책 단일 조회", description = "책 ID를 이용해 단일 책 정보를 조회합니다.")
  @GetMapping("/{bookId}")
  public ResponseEntity<BaseResponse<BookResponse>> getBookById(
      @Parameter(description = "조회할 책 식별자", example = "1") @PathVariable Long bookId) {
    BookResponse response = bookService.getBookById(bookId);
    return ResponseEntity.ok(BaseResponse.success("책 단일 조회에 성공했습니다", response));
  }

  @Operation(summary = "책 수정", description = "책 정보를 수정하고, 수정된 책 정보를 반환합니다.")
  @PutMapping(value = "/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<BaseResponse<BookResponse>> updateBook(
      @Parameter(description = "수정할 책 식별자", example = "1") @PathVariable Long bookId,
      @RequestPart(value = "imageList", required = false) List<MultipartFile> imageList,
      @RequestPart("book") @Valid BookRequest request,
      @RequestParam("categoryList") List<Category> categoryList) {
    BookResponse response = bookService.updateBook(bookId, request, categoryList, imageList);
    return ResponseEntity.ok(BaseResponse.success("책 수정에 성공했습니다.", response));
  }

  @Operation(summary = "책 삭제", description = "책 ID를 이용해 책을 삭제합니다.")
  @DeleteMapping("/{bookId}")
  public ResponseEntity<BaseResponse> deleteBook(
      @Parameter(description = "삭제할 책 식별자", example = "1") @PathVariable Long bookId) {
    bookService.deleteBook(bookId);
    return ResponseEntity.ok(BaseResponse.success("책 삭제에 성공했습니다."));
  }
}
