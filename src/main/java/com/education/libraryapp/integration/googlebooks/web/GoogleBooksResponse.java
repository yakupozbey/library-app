package com.education.libraryapp.integration.googlebooks.web;

import lombok.Builder;

@Builder
public record GoogleBooksResponse(
        String title,
        Double price,
        String ISBN13,
        String publisherName,
        String authorNameSurname
) {
}
