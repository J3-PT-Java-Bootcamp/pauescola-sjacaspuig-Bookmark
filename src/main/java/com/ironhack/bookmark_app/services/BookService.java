package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;

public interface BookService {

    void showAll();
    BookDTO saveBook(BookDTO bookDTO);
}
