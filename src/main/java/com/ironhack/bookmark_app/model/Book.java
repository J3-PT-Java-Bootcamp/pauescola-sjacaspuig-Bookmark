package com.ironhack.bookmark_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String key;

    @NotNull
    private String title;

    @NotNull
    private String author_name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="item")
    @JsonIgnore
    private List<Favourite> favourites;

    public Book(String id, String title, String author) {
        this.key = id;
        this.title = title;
        this.author_name = author;
    }

    public Book fromDTO(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getAuthor());
    }
}
