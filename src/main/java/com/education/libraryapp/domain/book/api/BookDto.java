package com.education.libraryapp.domain.book.api;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.publisher.api.PublisherDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String title;
    private BigDecimal price;
    private String isbn13;
    private PublisherDto publisher;
    private AuthorDto author;

}
