package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookDTO bookDTO);

    void showAll();
}
