package com.ironhack.bookmark_app.services.reports;

import com.ironhack.bookmark_app.enums.FavouriteStatus;
import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReportService {

    @Autowired
    UserRepository userRepository;

    public void reportUsersByBook() {
        userRepository.findAll().forEach(bookByUser -> {
            System.out.println(bookByUser.getName() + " " + bookByUser.getFavourites().size());
        });
    }

    public void reportUsersToReadByBook() {
        userRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.TODO) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getName() + " " + favs.size());
        });
    }

    public void reportUsersReadingByBook() {
        userRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.DOING) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getName() + " " + favs.size());
        });
    }

    public void reportUsersReadByBook() {
        userRepository.findAll().forEach(bookByUser -> {
            List<Favourite> favs = new ArrayList<>();
            for (Favourite favourite: bookByUser.getFavourites()) {
                if(favourite.getStatus() == FavouriteStatus.DONE) {
                    favs.add(favourite);
                }
            }
            System.out.println(bookByUser.getName() + " " + favs.size());
        });
    }
}
