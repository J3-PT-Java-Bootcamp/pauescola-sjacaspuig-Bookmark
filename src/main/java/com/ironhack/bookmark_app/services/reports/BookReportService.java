package com.ironhack.bookmark_app.services.reports;

import com.ironhack.bookmark_app.enums.FavouriteStatus;
import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookReportService {

    @Autowired
    BookRepository bookRepository;

    public void reportUsersByBook() {
        bookRepository.findAll().forEach(usersByBook -> {
            System.out.println(usersByBook.getTitle() + " " + usersByBook.getFavourites().size());
        });
    }

    public void reportUsersToReadByBook() {
        bookRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.TODO) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getTitle() + " " + favs.size());
        });
    }

    public void reportUsersReadingByBook() {
        bookRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.DOING) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getTitle() + " " + favs.size());
        });
    }

    public void reportUsersReadByBook() {
        bookRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.DONE) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getTitle() + " " + favs.size());
        });
    }
}
