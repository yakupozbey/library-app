package com.education.libraryapp.domain.author.impl;

import com.education.libraryapp.domain.author.api.AuthorDto;
import com.education.libraryapp.domain.author.api.AuthorMapper;
import com.education.libraryapp.domain.author.api.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public Author findAuthorByAuthorNameSurname(String authorNameSurname) {
        return repository.findByAuthorNameSurnameIgnoreCase(authorNameSurname)
                .orElse(null);
    }

    @Override
    public Author create(String authorNameSurname, UUID id) {
        Author newAuthor = new Author();
        newAuthor.setBookId(id);
        newAuthor.setAuthorNameSurname(authorNameSurname);
        return repository.save(newAuthor);
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(UUID id, AuthorDto dto) {

        Author author = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                Author.class.getSimpleName() + " not found with id: " + id));

        author.setAuthorNameSurname(dto.getAuthorNameSurname());

        if (dto.getBook() != null) {
            author.setBookId(dto.getBook().getId());
        }

        Author updatedAuthor = repository.save(author);
        return AuthorMapper.toDto(updatedAuthor);
    }


    @Override
    @Transactional(readOnly = true)
    public AuthorDto findAuthorByBookId(UUID bookId) {
        List<Author> authors = repository.findAllByBookId(bookId);
        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Author not found with bookId: " + bookId);
        }
        return AuthorMapper.toDto(authors.get(0));
    }

    @Override
    public AuthorDto getAuthorByBookId(UUID bookId) {
        return repository.findByBookId(bookId)
                .map(AuthorMapper::toDto)
                .orElse(null);
    }

    @Override
    public Page<AuthorDto> getAllAuthors(Pageable pageable) {
        return repository.findAll(pageable).map(AuthorMapper::toDto);
    }

    @Transactional
    @Override
    public void deleteByBookId(UUID bookId) {
        repository.deleteByBookId(bookId);
    }

}
