package com.education.libraryapp.domain.book.api;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.book.impl.Book;
import com.education.libraryapp.domain.book.web.BookRequest;
import com.education.libraryapp.domain.book.web.BookResponse;
import com.education.libraryapp.domain.publisher.api.PublisherDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class BookMapper {

    private BookMapper() {

    }

    public static BookDto toDto(BookRequest request) {
        return BookDto.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .isbn13(request.getIsbn13())
                .publisher(PublisherDto
                        .builder()
                        .publisherName(request.getPublisherName())
                        .build())
                .author(AuthorDto.builder()
                        .authorNameSurname(request.getAuthorNameSurname())
                        .build())
                .build();
    }

    public static BookResponse toResponse(BookDto dto) {
        return BookResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .isbn13(dto.getIsbn13())
                .publisherName(dto.getPublisher().getPublisherName())
                .authorNameSurname(dto.getAuthor() != null ? dto.getAuthor().getAuthorNameSurname() : null)
                .build();
    }

    public static Book toEntity(BookDto dto) {
        return Book.builder()
                .title(dto.getTitle())
                .price(dto.getPrice())
                .isbn13(dto.getIsbn13())
                .publisherId(dto.getPublisher().getId() != null ? dto.getPublisher().getId() : null)
                .build();
    }


    public static BookDto entityToDto(Book entity, PublisherDto publisher, AuthorDto author) {
        return BookDto.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .isbn13(entity.getIsbn13())
                .publisher(publisher)
                .author(author)
                .build();
    }

    public static Page<BookResponse> toPageResponse(Page<BookDto> books) {
        return books.map(BookMapper::toResponse);
    }

    public static List<BookResponse> toResponseList(List<BookDto> books) {
        return books.stream().map(BookMapper::toResponse).toList();
    }

}
