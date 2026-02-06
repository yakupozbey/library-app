package com.education.libraryapp.domain.author.api;

import com.education.libraryapp.domain.author.impl.Author;
import com.education.libraryapp.domain.author.web.AuthorResponse;
import org.springframework.data.domain.Page;

public class AuthorMapper {
    public AuthorMapper() {
    }

    public static AuthorResponse toResponse(AuthorDto dto) {
        return AuthorResponse.builder()
                .authorId(dto.getId())
                .authorNameSurname(dto.getAuthorNameSurname())
                .build();
    }


    public static AuthorDto toDto(Author entity) {
        return AuthorDto.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .authorNameSurname(entity.getAuthorNameSurname())
                .build();
    }

    public static Page<AuthorResponse> toPageResponse(Page<AuthorDto> authors) {
        return authors.map(AuthorMapper::toResponse);
    }

}
