package com.education.libraryapp.domain.author.api;

import com.education.libraryapp.domain.book.api.BookDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String authorNameSurname;
    private BookDto book;
}
