package com.education.libraryapp.domain.publisher.web;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PublisherResponse {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String publisherName;
}
