package com.education.libraryapp.domain.publisher.impl;

import com.education.libraryapp.domain.publisher.api.PublisherDto;
import com.education.libraryapp.domain.publisher.api.PublisherMapper;
import com.education.libraryapp.domain.publisher.api.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository repository;


    @Override
    public Publisher findPublisherByPublisherName(String publisherName) {
        return repository.findByPublisherNameIgnoreCase(publisherName)
                .orElse(null);
    }

    @Override
    public PublisherDto getPublisherById(UUID id) {
        return PublisherMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Publisher.class.getSimpleName() + " not found with id: " + id)));
    }

    @Override
    public Publisher create(String publisherName) {
        Publisher newPublisher = new Publisher();
        newPublisher.setPublisherName(publisherName);
        return repository.save(newPublisher);
    }

    @Override
    public Page<PublisherDto> getAllPublishers(Pageable pageable) {
        return repository.findAll(pageable).map(PublisherMapper::toDto);
    }

    @Override
    public Publisher updatePublisher(UUID id, String publisherName) {
        Publisher publisher = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Publisher.class.getSimpleName() + " not found with id: " + id));

        publisher.setPublisherName(publisherName);
        return repository.save(publisher);
    }

}
