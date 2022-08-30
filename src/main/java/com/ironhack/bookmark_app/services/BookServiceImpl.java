package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;


}
