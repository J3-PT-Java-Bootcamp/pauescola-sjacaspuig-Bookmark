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
    public void saveBook(Book book) {
        bookRepository.save(book);
        System.out.println(book.getTitle() + " correctly stored!!");
    }

    @Override
    public List<BookDTO> showAll() {
        var allBooks = bookRepository.findAll();
        List<BookDTO> booksToShow = new ArrayList<>();
        for (Book book:allBooks){
            booksToShow.add(new BookDTO().fromEntity(book));
        }
        return booksToShow;
    }

    @Override
    public void printList(List<BookDTO> bookDTOS) {
        for (BookDTO i : bookDTOS) {System.out.println(i.toString(true));}
    }
}
