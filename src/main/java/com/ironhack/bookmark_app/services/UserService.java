package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser();
    UserDTO findById(Long id) throws Exception;
    void showAll();
    void showById(Long id);
}
