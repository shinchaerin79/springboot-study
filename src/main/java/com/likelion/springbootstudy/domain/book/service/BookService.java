package com.likelion.springbootstudy.domain.book.service;

import com.likelion.springbootstudy.domain.book.dto.request.BookRequest;
import com.likelion.springbootstudy.domain.book.dto.response.BookResponse;
import com.likelion.springbootstudy.domain.book.entity.Book;
import com.likelion.springbootstudy.domain.book.entity.BookImage;
import com.likelion.springbootstudy.domain.book.entity.Category;
import com.likelion.springbootstudy.domain.book.exception.BookErrorCode;
import com.likelion.springbootstudy.domain.book.mapper.BookMapper;
import com.likelion.springbootstudy.domain.book.repository.BookRepository;
import com.likelion.springbootstudy.global.exception.CustomException;
import com.likelion.springbootstudy.global.s3.entity.PathName;
import com.likelion.springbootstudy.global.s3.service.S3Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

  private final BookRepository bookRepository;
  private final S3Service s3Service;
  private final BookMapper bookMapper;

  @Transactional

  // 책 생성
  public BookResponse createBook(BookRequest request, List<Category> categoryList,
      List<MultipartFile> imageList) {

    if (bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor()).isPresent()) {
      throw new CustomException(BookErrorCode.BOOK_ALREADY_EXISTS);
    }

    Book book = Book.builder()
        .title(request.getTitle())
        .author(request.getAuthor())
        .publisher(request.getPublisher())
        .price(request.getPrice())
        .description(request.getDescription())
        .releaseDate(request.getReleaseDate())
        .build();

    book.addCategoryList(categoryList == null ? List.of() : categoryList);

    book.addCategoryList(categoryList == null ? List.of() : categoryList);

    List<BookImage> bookImageList =
        Optional.ofNullable(imageList).orElse(List.of()).stream()
            .filter(image -> image != null && !image.isEmpty())
            .map(image -> {
              String imageUrl = s3Service.uploadFile(PathName.FOLDER1, image);
              return BookImage.builder()
                  .imageUrl(imageUrl)
                  .book(book)
                  .build();
            })
            .toList();

    book.addBookImageList(bookImageList);

    bookRepository.save(book);

    return bookMapper.toBookResponse(book);
  }

  //  책 전체 조회
  public List<BookResponse> getAllBooks() {
    List<Book> bookList = bookRepository.findAll();
    return bookList.stream().map(bookMapper::toBookResponse).toList();
  }

  // 책 정보 수정
  @Transactional
  public BookResponse updateBook(Long id, BookRequest request, List<Category> categoryList,
      List<MultipartFile> imageList) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new CustomException(BookErrorCode.BOOK_NOT_FOUND));

    // 값 수정
    Book update = Book.builder()
        .title(request.getTitle())
        .author(request.getAuthor())
        .publisher(request.getPublisher())
        .price(request.getPrice())
        .description(request.getDescription())
        .releaseDate(request.getReleaseDate())
        .build();
    book.update(update);

    // 카테고리 교체
    if (categoryList != null) {
      book.addCategoryList(categoryList);
    }

    // 이미지 교체 (선택)
    if (imageList != null && !imageList.isEmpty()) {
      if (book.getBookImageList() == null) {
        book.addBookImageList(new ArrayList<>());
      } else {
        book.getBookImageList().clear();
      }

      List<BookImage> newImages = imageList.stream()
          .filter(image -> image != null && !image.isEmpty())
          .map(image -> {
            String imageUrl = s3Service.uploadFile(PathName.FOLDER1, image);
            return BookImage.builder()
                .imageUrl(imageUrl)
                .book(book)
                .build();
          })
          .toList();

      book.getBookImageList().addAll(newImages);
    }

    bookRepository.save(book);
    return bookMapper.toBookResponse(book);
  }
}