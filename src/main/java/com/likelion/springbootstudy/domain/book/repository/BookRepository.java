package com.likelion.springbootstudy.domain.book.repository;

import com.likelion.springbootstudy.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
