package com.ironhack.bookmark_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.enums.FavouriteStatus;
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
@Table(name = "favourites")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @ManyToOne
    private Book item;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FavouriteStatus status;

    @ManyToMany(mappedBy = "favourites")
    @JsonIgnore
    private List<User> users;

    public Favourite(Long id, Book item, FavouriteStatus status) {
        this.id = id;
        this.item = item;
        this.status = status;
    }

    public Favourite fromDTO(FavouriteDTO favouriteDTO) {
        return new Favourite((favouriteDTO.getId()), favouriteDTO.getItem(), favouriteDTO.getStatus());
    }
}
