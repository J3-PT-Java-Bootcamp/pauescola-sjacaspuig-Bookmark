package com.ironhack.bookmark_app.dto;

import com.ironhack.bookmark_app.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private String id;
    private String title;
    private String author;

    public BookDTO fromEntity(Book book) {
        return new BookDTO(book.getKey(), book.getTitle(), book.getAuthor_name());
    }
}
