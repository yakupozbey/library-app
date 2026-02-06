package com.education.libraryapp.domain.publisher.web;

import com.education.libraryapp.domain.publisher.api.PublisherMapper;
import com.education.libraryapp.domain.publisher.api.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService service;

    @GetMapping
    public ResponseEntity<Page<PublisherResponse>> getAllPublishers(Pageable pageable) {
        return ResponseEntity.ok(PublisherMapper.toPageResponse(service.getAllPublishers(pageable)));
    }

}
