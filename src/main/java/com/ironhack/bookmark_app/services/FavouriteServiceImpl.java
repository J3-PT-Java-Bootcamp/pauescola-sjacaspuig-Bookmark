package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    FavouriteRepository favouriteRepository;
}
