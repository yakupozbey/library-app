package com.education.libraryapp.domain.publisher.api;

import com.education.libraryapp.domain.publisher.impl.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PublisherService {

    Publisher findPublisherByPublisherName(String publisherName);

    PublisherDto getPublisherById(UUID id);

    Publisher create(String publisherName);

    Page<PublisherDto> getAllPublishers(Pageable pageable);

    Publisher updatePublisher(UUID id, String publisherName);
}
