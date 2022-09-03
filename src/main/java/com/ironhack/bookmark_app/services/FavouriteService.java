package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.error.NotFoundException;

public interface FavouriteService {

    FavouriteDTO saveFavourite(FavouriteDTO favouriteDTO);
    FavouriteDTO updateStatus(Long userID) throws NotFoundException;
    FavouriteDTO findById(Long id) throws NotFoundException;
    void showAll();
    void showById(Long id);

    void removeById(Long id) throws NotFoundException;
}
