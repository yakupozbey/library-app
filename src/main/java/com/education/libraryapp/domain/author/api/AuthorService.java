package com.education.libraryapp.domain.author.api;


import com.education.libraryapp.domain.author.impl.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthorService {
    Author findAuthorByAuthorNameSurname(String authorNameSurname);

    Author create(String authorNameSurname, UUID id);

    AuthorDto updateAuthor(UUID id, AuthorDto dto);

    AuthorDto findAuthorByBookId(UUID bookId);

    AuthorDto getAuthorByBookId(UUID bookId);

    Page<AuthorDto> getAllAuthors(Pageable pageable);

    void deleteByBookId(UUID bookId);
}
