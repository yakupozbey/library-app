package com.education.libraryapp.domain.book.web;

import com.education.libraryapp.domain.book.api.BookMapper;
import com.education.libraryapp.domain.book.api.BookService;
import com.education.libraryapp.integration.googlebooks.api.GoogleBooksService;
import com.education.libraryapp.integration.googlebooks.web.GoogleBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    private final GoogleBooksService googleBooksService;


    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        return ResponseEntity.ok(BookMapper.toResponse(service.createBook(BookMapper.toDto(request))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(BookMapper.toResponse(service.getBookById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(BookMapper.toPageResponse(service.getAllBooks(pageable)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID id) {
        service.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> uptadeBook(@PathVariable UUID id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(BookMapper.toResponse(service.updateBook(id, BookMapper.toDto(request))));
    }

    @GetMapping("/starts-with")
    public List<BookResponse> getBooksStartingWith(@RequestParam String prefix) {
        return BookMapper.toResponseList(service.getBooksStartingWith(prefix));
    }


    //BELLİ BİR TARİHTEN SONRAKİ BOOK'LARI GETİRİR ISO TARİH INPUT OLMALI
//    @GetMapping("/created-after")
//    public Page<BookResponse> getBooksCreatedAfter(
//            @RequestParam("date")
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//            LocalDateTime date,
//            Pageable pageable
//    ) {
//        return BookMapper.toPageResponse(service.getBooksCreatedAfter(date, pageable));
//    }

    @GetMapping("/created-after")
    public Page<BookResponse> getBooksCreatedAfter(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime date,

            @RequestParam(required = false) Integer year,
            Pageable pageable
    ) {
        LocalDateTime after = resolveAfter(date, year);
        return BookMapper.toPageResponse(service.getBooksCreatedAfter(after, pageable));
    }

    private LocalDateTime resolveAfter(LocalDateTime date, Integer year) {
        if (date != null) return date;
        if (year != null) return LocalDateTime.of(year, 1, 1, 0, 0, 0);
        throw new IllegalArgumentException("Either 'date' (ISO) or 'year' must be provided.");
    }

    @GetMapping("/google-search")
    public List<GoogleBooksResponse> searchFromGoogle(@RequestParam String title) {
        return googleBooksService.searchByTitle(title);
    }
}
