package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.error.NotFoundException;

import java.util.List;

public interface UserService {
    UserDTO createUser();
    UserDTO findById(Long id) throws Exception;
    void showAll();
    void showById(Long id);
    UserDTO addBook(Long userId, BookDTO bookDTO) throws NotFoundException;
}
