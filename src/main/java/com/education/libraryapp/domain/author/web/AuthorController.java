package com.education.libraryapp.domain.author.web;

import com.education.libraryapp.domain.author.api.AuthorMapper;
import com.education.libraryapp.domain.author.api.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService service;

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> getAllAuthors(Pageable pageable) {
        return ResponseEntity.ok(AuthorMapper.toPageResponse(service.getAllAuthors(pageable)));
    }
}
