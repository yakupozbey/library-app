package com.education.libraryapp.integration.googlebooks.api;

import com.education.libraryapp.integration.googlebooks.web.GoogleBooksResponse;

import java.util.List;

public interface GoogleBooksService {
    List<GoogleBooksResponse> searchByTitle(String title);
}
