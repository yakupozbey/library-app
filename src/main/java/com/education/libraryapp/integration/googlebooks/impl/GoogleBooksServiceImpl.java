package com.education.libraryapp.integration.googlebooks.impl;

import com.education.libraryapp.integration.googlebooks.api.GoogleBook;
import com.education.libraryapp.integration.googlebooks.api.GoogleBooksService;
import com.education.libraryapp.integration.googlebooks.web.GoogleBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoogleBooksServiceImpl implements GoogleBooksService {
    private final GoogleBooksFeignClient client;

    public List<GoogleBooksResponse> searchByTitle(String title) {
        String query = "intitle:" + title;

        GoogleBook response = client.search(query);
        if (response == null || response.items() == null) return List.of();

        String normalized = title.trim().toLowerCase();

        return response.items().stream()
                .map(this::toDto)
                .filter(dto -> dto.title() != null && dto.title().trim().toLowerCase().equals(normalized))
                .toList();
    }

    private GoogleBooksResponse toDto(GoogleBook.Item item) {
        var vi = item.volumeInfo();

        String isbn13 = null;
        if (vi != null && vi.industryIdentifiers() != null) {
            isbn13 = vi.industryIdentifiers().stream()
                    .filter(x -> "ISBN_13".equalsIgnoreCase(x.type()))
                    .map(GoogleBook.IndustryIdentifier::identifier)
                    .findFirst()
                    .orElse(null);
        }

        String author = (vi != null && vi.authors() != null && !vi.authors().isEmpty())
                ? vi.authors().get(0)
                : null;

        Double price = (item.saleInfo() != null && item.saleInfo().listPrice() != null)
                ? item.saleInfo().listPrice().amount()
                : null;

        return GoogleBooksResponse.builder()
                .title(vi != null ? vi.title() : null)
                .price(price)
                .ISBN13(isbn13)
                .publisherName(vi != null ? vi.publisher() : null)
                .authorNameSurname(author)
                .build();
    }
}
