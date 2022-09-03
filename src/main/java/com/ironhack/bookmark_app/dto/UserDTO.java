package com.ironhack.bookmark_app.dto;

import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private List<Favourite> favourites;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getFavourites());
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
        for (Favourite favourite : favourites) {
            var favouriteDTO = FavouriteDTO.fromEntity(favourite);
            sb.append("  ").append(favouriteDTO.toStringHorizontal());
            if(counter != favourites.size()) sb.append("\n");
            counter++;
        }
        sb.append("\n]");
        return sb.toString();
    }
}

