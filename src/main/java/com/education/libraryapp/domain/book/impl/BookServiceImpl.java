package com.education.libraryapp.domain.book.impl;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.author.api.AuthorService;
import com.education.libraryapp.domain.author.impl.Author;
import com.education.libraryapp.domain.book.api.BookDto;
import com.education.libraryapp.domain.book.api.BookMapper;
import com.education.libraryapp.domain.book.api.BookService;
import com.education.libraryapp.domain.publisher.api.PublisherDto;
import com.education.libraryapp.domain.publisher.api.PublisherService;
import com.education.libraryapp.domain.publisher.impl.Publisher;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    @Override
    @Transactional
    public BookDto createBook(BookDto dto) {
        Publisher publisher = resolvePublisher(dto);
        Book savedBook = saveBook(dto, publisher);
        resolveAuthor(dto, savedBook);
        return buildResponse(savedBook, publisher);
    }


    @Transactional(readOnly = true)
    @Override
    public BookDto getBookById(UUID bookId) {
        return repository.findById(bookId)
                .map(book -> {
                    PublisherDto publisherDto = publisherService.getPublisherById(book.getPublisherId());
                    AuthorDto authorDto = authorService.getAuthorByBookId(book.getId());
                    return BookMapper.entityToDto(book, publisherDto, authorDto);
                })
                .orElseThrow(() -> new EntityNotFoundException(Book.class.getSimpleName() + " not found with id: " + bookId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return repository.findAll(pageable)
                .map(book -> {
                    PublisherDto publisherDto = publisherService.getPublisherById(book.getPublisherId());
                    AuthorDto authorDto = authorService.getAuthorByBookId(book.getId());
                    return BookMapper.entityToDto(book, publisherDto, authorDto);
                });
    }

    @Transactional
    @Override
    public void deleteBookById(UUID id) {
        authorService.deleteByBookId(id);
        repository.deleteById(id);
    }

    @Override
    public BookDto updateBook(UUID id, BookDto dto) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        Publisher publisher = resolvePublisher(dto);
//        Book savedBook = saveBook(dto, publisher);

        book.setTitle(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setIsbn13(dto.getIsbn13());
        book.setPublisherId(publisher.getId());

        Book savedBook = repository.save(book);
        resolveAuthor(dto, savedBook);
        return buildResponse(savedBook, publisher);
    }

    private Publisher resolvePublisher(BookDto dto) {
        String publisherName = dto.getPublisher().getPublisherName();

        Publisher publisher = publisherService.findPublisherByPublisherName(publisherName);
        if (publisher == null) {
            publisher = publisherService.create(publisherName);
        } else {
            publisherService.updatePublisher(publisher.getId(), publisherName);

        }
        return publisher;
    }

    private Book saveBook(BookDto dto, Publisher publisher) {
        Book book = BookMapper.toEntity(dto);
        book.setPublisherId(publisher.getId());
        return repository.save(book);
    }

    private void resolveAuthor(BookDto dto, Book savedBook) {
        String authorNameSurname = dto.getAuthor().getAuthorNameSurname();

        Author author = authorService.findAuthorByAuthorNameSurname(authorNameSurname);
        if (author == null) {
            authorService.create(authorNameSurname, savedBook.getId());
        } else {
            AuthorDto authorUpdateDto = AuthorDto.builder()
                    .authorNameSurname(authorNameSurname)
                    .book(BookDto.builder()
                            .id(savedBook.getId())
                            .build())
                    .build();
            authorService.updateAuthor(author.getId(), authorUpdateDto);
        }
    }

    private BookDto buildResponse(Book savedBook, Publisher publisher) {
        AuthorDto authorDto = authorService.findAuthorByBookId(savedBook.getId());
        PublisherDto publisherDto = publisherService.getPublisherById(publisher.getId());
        return BookMapper.entityToDto(savedBook, publisherDto, authorDto);
    }


    @Override
    public List<BookDto> getBooksStartingWith(String prefix) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle()
                        .toLowerCase()
                        .startsWith(prefix.toLowerCase()))
                .map(book -> {
                    PublisherDto publisherDto = publisherService.getPublisherById(book.getPublisherId());
                    AuthorDto authorDto = authorService.getAuthorByBookId(book.getId());
                    return BookMapper.entityToDto(book, publisherDto, authorDto);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> getBooksCreatedAfter(LocalDateTime date, Pageable pageable) {

        return repository.findBooksCreatedAfter(date, pageable)
                .map(book -> {
                    PublisherDto publisherDto = publisherService.getPublisherById(book.getPublisherId());
                    AuthorDto authorDto = authorService.getAuthorByBookId(book.getId());
                    return BookMapper.entityToDto(book, publisherDto, authorDto);
                });
    }

}
