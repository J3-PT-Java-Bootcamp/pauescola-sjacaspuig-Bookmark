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
    private String authorName;

    public static BookDTO fromEntity(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthorName());
    }

    @Override
    public String toString() {
        return  "Title: " + title + "   "+"Author(s): " + authorName +".";
    }
}
