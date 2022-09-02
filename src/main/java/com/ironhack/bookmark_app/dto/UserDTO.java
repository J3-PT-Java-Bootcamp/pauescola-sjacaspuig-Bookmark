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

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getFavourites());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User {\n");
        sb.append("   id=").append(id).append("\n");
        sb.append("   name='").append(name).append('\'').append("\n");
        sb.append("   favourites=").append(favourites).append("\n");
        sb.append('}');
        return sb.toString();
    }
}
