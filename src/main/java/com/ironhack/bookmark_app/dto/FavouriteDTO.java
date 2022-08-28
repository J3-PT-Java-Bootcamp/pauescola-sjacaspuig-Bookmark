package com.ironhack.bookmark_app.dto;

import com.ironhack.bookmark_app.enums.FavouriteStatus;
import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.model.Favourite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavouriteDTO {

    private Long id;
    private Book item;
    private FavouriteStatus status;

    public FavouriteDTO fromEntity(Favourite favourite) {
        return new FavouriteDTO(favourite.getId(), favourite.getItem(), favourite.getStatus());
    }
}
