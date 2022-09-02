package com.ironhack.bookmark_app.model;

import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Favourite> favourites;

    public User(String name) {
        this.name = name;
        this.favourites = List.of();
    }

    public static User fromDTO(UserDTO userDTO) {
        var user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());

        List<Favourite> favourites = List.of();
        for (FavouriteDTO favouriteDTO: userDTO.getFavourites()) {
            var favourite = Favourite.fromDTO(favouriteDTO);
            favourites.add(favourite);
        }
        user.setFavourites(favourites);

        return user;
    }
}
