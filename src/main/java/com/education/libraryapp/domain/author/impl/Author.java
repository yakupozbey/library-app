package com.education.libraryapp.domain.author.impl;

import com.education.libraryapp.library.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = Author.TABLE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Author extends AbstractEntity {
    public static final String TABLE = "author";
    public static final String COL_AUTHOR_NAME_SURNAME = "authorNameSurname";
    public static final String COL_BOOK_ID = "book_Id";

    @Column(name = COL_AUTHOR_NAME_SURNAME, nullable = false)
    private String authorNameSurname;

    @Column(name = COL_BOOK_ID)
    private UUID bookId;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", authorNameSurname='" + authorNameSurname + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
