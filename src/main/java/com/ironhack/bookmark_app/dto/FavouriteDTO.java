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

    public static FavouriteDTO fromEntity(Favourite favourite) {
        return new FavouriteDTO(favourite.getId(), favourite.getItem(), favourite.getStatus());
    }

    @Override
    public String toString() {
        return "[" + id + "]" + "\n" +
                "Book: " + BookDTO.fromEntity(item).toString(false) + "\n" +
                "Status: " + statusToString() + "\n";
    }

    private String statusToString() {
        var s = "";
        switch (status) {
            case TODO -> s =  "TO READ";
            case DOING -> s = "READING";
            case DONE -> s = "READ";
        }
        return s;
    }
}
