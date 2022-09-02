package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    FavouriteRepository favouriteRepository;

    @Override
    public FavouriteDTO saveFavourite(FavouriteDTO favouriteDTO) {
        var favourite = Favourite.fromDTO(favouriteDTO);
        favourite.setId(null);
        Favourite favouriteSaved = favouriteRepository.save(favourite);
        return FavouriteDTO.fromEntity(favouriteSaved);
    }
}
