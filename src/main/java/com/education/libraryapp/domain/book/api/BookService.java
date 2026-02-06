package com.education.libraryapp.domain.book.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookService {
    BookDto createBook(BookDto dto);

    BookDto getBookById(UUID bookId);

    Page<BookDto> getAllBooks(Pageable pageable);

    void deleteBookById(UUID id);

    BookDto updateBook(UUID id, BookDto dto);

    List<BookDto> getBooksStartingWith(String prefix);

    Page<BookDto> getBooksCreatedAfter(LocalDateTime date, Pageable pageable);
}
