package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;

import org.json.simple.parser.ParseException;
import java.util.List;

public interface ApiService {

    void searchBook() throws ParseException;

    List<BookDTO> findByTitle() throws ParseException;
}
