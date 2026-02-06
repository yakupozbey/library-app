package com.education.libraryapp.domain.author.web;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthorRequest {

    @NotBlank
    private String authorNameSurname;
}
