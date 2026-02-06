package com.education.libraryapp.domain.book.web;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    private String title;
    private BigDecimal price;
    private String isbn13;
    private String publisherName;
    private String authorNameSurname;

}
