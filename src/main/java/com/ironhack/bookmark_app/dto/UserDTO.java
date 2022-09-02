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
    private List<FavouriteDTO> favourites;

    public static UserDTO fromEntity(User user) {
        List<FavouriteDTO> favouritesDTO = List.of();
        for (Favourite favourite: user.getFavourites()) {
            var favouriteDTO = FavouriteDTO.fromEntity(favourite);
            favouritesDTO.add(favouriteDTO);
        }
        return new UserDTO(user.getId(), user.getName(), favouritesDTO);
    }

    @Override
    public String toString() {
        return "[" + id + "]" + "\n" +
                "Name: " + name + "\n" +
                "Favourites: " + favouritesToString() + "\n";
    }

    private String favouritesToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append("\n");
        var counter = 1;
        for (FavouriteDTO favourite : favourites) {
            sb.append(favourite);
            if(counter != favourites.size()) sb.append("\n");
            counter++;
        }
        sb.append("]");
        return sb.toString();
    }
}

