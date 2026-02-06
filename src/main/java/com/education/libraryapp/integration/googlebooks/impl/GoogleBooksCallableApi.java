package com.education.libraryapp.integration.googlebooks.impl;

import com.education.libraryapp.integration.googlebooks.api.GoogleBook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GoogleBooksCallableApi {

    @GetMapping("/volumes")
    GoogleBook search(@RequestParam("q") String query);
}
