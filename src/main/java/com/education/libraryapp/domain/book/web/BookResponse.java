package com.education.libraryapp.domain.book.web;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String title;
    private BigDecimal price;
    private String isbn13;
    private String publisherName;
    private String authorNameSurname;
}
