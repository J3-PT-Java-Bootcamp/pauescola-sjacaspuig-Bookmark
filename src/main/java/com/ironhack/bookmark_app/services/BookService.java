package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.error.NotFoundException;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);
    BookDTO findById(String id) throws NotFoundException;
    void showAll();
    void showById(String id);
}
