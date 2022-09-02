package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.model.User;
import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void showAll() {
        bookRepository.findAll().forEach((Book book) -> {
            var bookDTO = BookDTO.fromEntity(book);
            System.out.println("\n" + bookDTO.toString()  + "\n");
        });
    }

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        var book = Book.fromDTO(bookDTO);
        var bookSaved = bookRepository.save(book);
        System.out.println(book.getTitle() + " correctly stored!!");
        return BookDTO.fromEntity(bookSaved);
    }
}
