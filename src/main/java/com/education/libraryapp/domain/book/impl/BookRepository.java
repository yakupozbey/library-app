package com.education.libraryapp.domain.book.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;


public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("select b from Book b where b.created > :date")
    Page<Book> findBooksCreatedAfter(@Param("date") LocalDateTime date, Pageable pageable);
}
