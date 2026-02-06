package com.education.libraryapp.domain.book.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PublisherWithBooksDto {
    private UUID publisherId;
    private String publisherName;
    private List<BookDto> books;
}
