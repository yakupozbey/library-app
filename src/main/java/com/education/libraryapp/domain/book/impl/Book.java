package com.education.libraryapp.domain.book.impl;

import com.education.libraryapp.library.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = Book.TABLE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Book extends AbstractEntity {
    public static final String TABLE = "book";
    public static final String COL_TITLE = "title";
    public static final String COL_PRICE = "price";
    public static final String COL_ISBN13 = "isbn13";
    public static final String COL_PUBLISHER_ID = "publisher_id";

    @Column(name = COL_TITLE, nullable = false)
    private String title;

    @Column(name = COL_PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = COL_ISBN13, nullable = false)
    private String isbn13;

    @Column(name = COL_PUBLISHER_ID, nullable = false)
    private UUID publisherId;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", isbn13='" + isbn13 + '\'' +
                ", publisherId=" + publisherId +
                '}';
    }
}
