package com.example.demo.library.repository;

import com.example.demo.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    List<Book> findAllByIsBorrowedTrue();
    List<Book> findAllByIsBorrowedFalse();
}
