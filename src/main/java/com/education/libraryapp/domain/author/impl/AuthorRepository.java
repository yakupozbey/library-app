package com.education.libraryapp.domain.author.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByAuthorNameSurnameIgnoreCase(String authorNameSurname);

    Optional<Author> findByBookId(UUID bookId);

    @Modifying
    @Query("delete from Author a where a.bookId = :bookId")
    void deleteByBookId(UUID bookId);

    @Query("select a from Author a where a.id = :id or a.bookId = :id")
    Optional<Author> findByIdOrBookId(UUID id);
}
