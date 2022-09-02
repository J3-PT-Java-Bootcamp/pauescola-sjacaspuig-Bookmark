package com.ironhack.bookmark_app.model;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String authorName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="item")
    private List<Favourite> favourites;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.authorName = author;
    }

    public static Book fromDTO(BookDTO bookDTO) {
        var book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthorName(bookDTO.getAuthorName());
        return book;
    }
}
