package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;

import com.ironhack.bookmark_app.error.NotFoundException;
import org.json.simple.parser.ParseException;
import java.util.List;

public interface ApiService {

    void searchBook() throws ParseException, NotFoundException;

    List<BookDTO> findByTitle() throws ParseException;
}
