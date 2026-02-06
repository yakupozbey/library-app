package com.education.libraryapp.domain.publisher.impl;

import com.education.libraryapp.library.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = Publisher.TABLE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Publisher extends AbstractEntity {
    public static final String TABLE = "publisher";
    public static final String COL_PUBLISHER_NAME = "publisherName";

    @Column(name = COL_PUBLISHER_NAME, nullable = false)
    private String publisherName;

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + getId() +
                ", publisherName='" + publisherName + '\'' +
                '}';
    }
}
