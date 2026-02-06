package com.education.libraryapp.domain.author.web;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private UUID authorId;
    private String authorNameSurname;
}
