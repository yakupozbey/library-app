package com.education.libraryapp.domain.publisher.api;

import com.education.libraryapp.domain.publisher.impl.Publisher;
import com.education.libraryapp.domain.publisher.web.PublisherResponse;
import org.springframework.data.domain.Page;

public class PublisherMapper {
    public PublisherMapper() {
    }

    public static PublisherResponse toResponse(PublisherDto dto) {
        return PublisherResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .publisherName(dto.getPublisherName())
                .build();
    }


    public static PublisherDto toDto(Publisher entity) {
        return PublisherDto.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .publisherName(entity.getPublisherName())
                .build();
    }

    public static Page<PublisherResponse> toPageResponse(Page<PublisherDto> publishers) {
        return publishers.map(PublisherMapper::toResponse);
    }
}
