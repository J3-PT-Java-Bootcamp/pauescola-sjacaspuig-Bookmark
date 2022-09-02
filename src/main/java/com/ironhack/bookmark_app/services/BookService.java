package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.model.Book;

import java.util.List;

public interface BookService {
    void saveBook(Book book);

    List<BookDTO> showAll();

    void printList(List<BookDTO> bookDTOS);
}
