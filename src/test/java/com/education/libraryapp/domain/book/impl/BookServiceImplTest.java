package com.education.libraryapp.domain.book.impl;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.author.api.AuthorService;
import com.education.libraryapp.domain.book.api.BookDto;
import com.education.libraryapp.domain.publisher.api.PublisherDto;
import com.education.libraryapp.domain.publisher.api.PublisherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @Mock
    private PublisherService publisherService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    void getBookById_whenBookNotFound_shouldThrowEntityNotFoundException() {
        UUID bookId = UUID.randomUUID();
        when(repository.findById(bookId)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                jakarta.persistence.EntityNotFoundException.class,
                () -> bookService.getBookById(bookId)
        );

        verify(repository).findById(bookId);
        verifyNoMoreInteractions(repository, publisherService, authorService);
    }

    @Test
    void getBookById_whenBookExists_shouldReturnDtoWithPublisherAndAuthor() {
        UUID bookId = UUID.randomUUID();
        UUID publisherId = UUID.randomUUID();

        Book book = Book.builder()
                .title("Clean Code")
                .price(new BigDecimal("45.0"))
                .isbn13("9780132350884")
                .publisherId(publisherId)
                .build();
        book.setId(bookId);

        PublisherDto publisherDto = PublisherDto.builder()
                .id(publisherId)
                .publisherName("Prentice Hall")
                .build();

        AuthorDto authorDto = AuthorDto.builder()
                .id(UUID.randomUUID())
                .authorNameSurname("Robert C. Martin")
                .build();

        given(repository.findById(bookId)).willReturn(Optional.of(book));
        given(publisherService.getPublisherById(publisherId)).willReturn(publisherDto);
        given(authorService.getAuthorByBookId(bookId)).willReturn(authorDto);

        BookDto result = bookService.getBookById(bookId);

        assertNotNull(result);

        assertAll(
                () -> assertEquals(bookId, result.getId()),
                () -> assertEquals("Clean Code", result.getTitle()),
                () -> assertEquals(new BigDecimal("45.0"), result.getPrice()),
                () -> assertEquals("9780132350884", result.getIsbn13()),
                () -> assertNotNull(result.getPublisher()),
                () -> assertEquals(publisherId, result.getPublisher().getId()),
                () -> assertEquals("Prentice Hall", result.getPublisher().getPublisherName()),
                () -> assertNotNull(result.getAuthor()),
                () -> assertEquals("Robert C. Martin", result.getAuthor().getAuthorNameSurname())
        );

        then(repository).should(times(1)).findById(bookId);
        then(publisherService).should(times(1)).getPublisherById(publisherId);
        then(authorService).should(times(1)).getAuthorByBookId(bookId);
        verifyNoMoreInteractions(repository, publisherService, authorService);
    }


}
