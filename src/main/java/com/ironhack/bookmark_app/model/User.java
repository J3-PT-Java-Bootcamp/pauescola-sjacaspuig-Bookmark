package com.ironhack.bookmark_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String name;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Favourite> favourites;

    public User(String name) {
        this.name = name;
        this.favourites = new ArrayList<>();
    }

    public User fromDTO(UserDTO userDTO) {
        var user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setFavourites(userDTO.getFavourites());
        return user;
    }
}
