package com.ironhack.bookmark_app.dto;

import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private List<Favourite> favourites;

    public UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getFavourites());
    }
}
