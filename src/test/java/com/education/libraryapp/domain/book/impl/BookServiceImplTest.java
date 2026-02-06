package com.education.libraryapp.domain.book.impl;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.author.api.AuthorService;
import com.education.libraryapp.domain.book.api.BookDto;
import com.education.libraryapp.domain.publisher.api.PublisherDto;
import com.education.libraryapp.domain.publisher.api.PublisherService;
import com.education.libraryapp.domain.publisher.impl.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void createBook_whenPublisherAndAuthorNotExist_shouldCreatePublisher_SaveBook_CreateAuthor_ReturnDto() {
        // GIVEN
        String publisherName = "Prentice Hall";
        String authorName = "Robert C. Martin";

        BookDto inputDto = BookDto.builder()
                .title("Clean Code")
                .price(new BigDecimal("45.0"))
                .isbn13("9780132350884")
                .publisher(PublisherDto.builder().publisherName(publisherName).build())
                .author(AuthorDto.builder().authorNameSurname(authorName).build())
                .build();

        // publisher bulunamazsa create edilecek
        when(publisherService.findPublisherByPublisherName(publisherName)).thenReturn(null);

        Publisher createdPublisher = new Publisher();
        UUID publisherId = UUID.randomUUID();
        createdPublisher.setId(publisherId); // AbstractEntity setId varsa çalışır
        createdPublisher.setPublisherName(publisherName);

        when(publisherService.create(publisherName)).thenReturn(createdPublisher);

        // repository.save(book) -> id üretmiş gibi davranalım
        UUID savedBookId = UUID.randomUUID();
        when(repository.save(any(Book.class))).thenAnswer(invocation -> {
            Book b = invocation.getArgument(0);
            // Book AbstractEntity'den geliyorsa setId vardır; yoksa aşağıdaki satır compile etmeyebilir.
            b.setId(savedBookId);
            return b;
        });

        // author yoksa create çağrılacak
        when(authorService.findAuthorByAuthorNameSurname(authorName)).thenReturn(null);

        AuthorDto authorDto = AuthorDto.builder()
                .id(UUID.randomUUID())
                .authorNameSurname(authorName)
                .build();

        when(authorService.findAuthorByBookId(savedBookId)).thenReturn(authorDto);

        PublisherDto publisherDto = PublisherDto.builder()
                .id(publisherId)
                .publisherName(publisherName)
                .build();

        when(publisherService.getPublisherById(publisherId)).thenReturn(publisherDto);

        // WHEN
        BookDto result = bookService.createBook(inputDto);

        // THEN
        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
        assertNotNull(result.getPublisher());
        assertEquals(publisherName, result.getPublisher().getPublisherName());
        assertNotNull(result.getAuthor());
        assertEquals(authorName, result.getAuthor().getAuthorNameSurname());

        // publisher create çağrıldı mı?
        verify(publisherService).findPublisherByPublisherName(publisherName);
        verify(publisherService).create(publisherName);

        // book save edilirken publisherId set edilmiş mi?
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(repository, atLeastOnce()).save(bookCaptor.capture());

        Book firstSaved = bookCaptor.getAllValues().get(0);
        assertEquals(publisherId, firstSaved.getPublisherId());

        // author create doğru bookId ile çağrıldı mı?
        verify(authorService).create(authorName, savedBookId);

        // response için gerekli servis çağrıları
        verify(authorService).findAuthorByBookId(savedBookId);
        verify(publisherService).getPublisherById(publisherId);

        // createBook içinde 2 kere save var (biri Book, biri savedBook tekrar)
        verify(repository, times(2)).save(any(Book.class));
    }

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
        // Arrange
        UUID bookId = UUID.randomUUID();
        UUID publisherId = UUID.randomUUID();

        Book book = Book.builder()
                .title("Clean Code")
                .price(new BigDecimal("45.0"))
                .isbn13("9780132350884")
                .publisherId(publisherId)
                .build();
        book.setId(bookId); // AbstractEntity.setId public ise

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

        // Act
        BookDto result = bookService.getBookById(bookId);

        // Assert (state)
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

        // Assert (behavior)
        then(repository).should(times(1)).findById(bookId);
        then(publisherService).should(times(1)).getPublisherById(publisherId);
        then(authorService).should(times(1)).getAuthorByBookId(bookId);
        verifyNoMoreInteractions(repository, publisherService, authorService);
    }


}
