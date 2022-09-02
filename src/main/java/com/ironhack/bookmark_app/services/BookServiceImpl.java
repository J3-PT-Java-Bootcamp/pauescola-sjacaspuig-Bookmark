package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
        System.out.println(book.getTitle() + " correctly stored!!");
    }
}
