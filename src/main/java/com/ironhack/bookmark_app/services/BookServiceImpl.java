package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.error.NotFoundException;
import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void showAll() {
        bookRepository.findAll().forEach((Book book) -> {
            var bookDTO = BookDTO.fromEntity(book);
            System.out.println("\n" + bookDTO.toString(true));
        });
    }

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        var book = Book.fromDTO(bookDTO);
        var bookSaved = bookRepository.save(book);
        System.out.println(book.getTitle() + " was correctly stored!!");
        return BookDTO.fromEntity(bookSaved);
    }

    @Override
    public BookDTO findById(String id) throws NotFoundException {
        final var book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException());
        var bookDTO = BookDTO.fromEntity(book);
        return bookDTO;
    }

    @Override
    public void showById(String id) {
        try {
            var bookDTO = findById(id);
            System.out.println("\n" + bookDTO.toString(true) + "\n");

        } catch(NotFoundException error) {
            System.out.println("\n" + error.getMessage() + "\n");
        }
    }
}
