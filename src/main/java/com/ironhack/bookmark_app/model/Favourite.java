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

    public Favourite(Book item) {
        this.item = item;
        this.status = FavouriteStatus.TODO;
    }

    public static Favourite fromDTO(FavouriteDTO favouriteDTO) {
        var favourite = new Favourite();
        favourite.setId(favouriteDTO.getId());
        favourite.setItem(favouriteDTO.getItem());
        favourite.setStatus(favouriteDTO.getStatus());
        return favourite;
    }
}
