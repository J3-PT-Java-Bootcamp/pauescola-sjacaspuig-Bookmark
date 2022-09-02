package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.dto.BookDTO;
import com.ironhack.bookmark_app.dto.FavouriteDTO;
import com.ironhack.bookmark_app.dto.UserDTO;
import com.ironhack.bookmark_app.error.NotFoundException;
import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.model.Favourite;
import com.ironhack.bookmark_app.model.User;
import com.ironhack.bookmark_app.repository.UserRepository;
import com.ironhack.bookmark_app.userinput.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookService bookService;

    @Override
    public UserDTO createUser() {

        System.out.print("\nIndicates a user name:\n");
        final String name = UserInput.readText();

        var user = new User(name);
        var userCreated = userRepository.save(user);

        System.out.println("\nThe ID of " + userCreated.getName() + " is " + userCreated.getId());

        var userCreatedDTO = UserDTO.fromEntity(userCreated);
        return userCreatedDTO;
    }

    @Override
    public UserDTO findById(Long id) throws NotFoundException {
        final var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        var userDTO = UserDTO.fromEntity(user);
        return userDTO;
    }

    @Override
    public void showAll() {
        userRepository.findAll().forEach((User user) -> {
            var userDTO = UserDTO.fromEntity(user);
            System.out.println("\n" + userDTO);
        });
    }

    @Override
    public void showById(Long id) {
        try {
            var userDTO = findById(id);
            System.out.println("\n" + userDTO.toString() + "\n");

        } catch(NotFoundException error) {
            System.out.println("\n" + error.getMessage() + "\n");
        }
    }

    @Override
    public UserDTO assignBook(Long userId, String bookId) throws NotFoundException {
        var bookDTO = bookService.findById(bookId);
        var userDTO = findById(userId);

        if(hasFavouriteSaved(userDTO, bookDTO)) {
            System.out.println("User already have this book as favourite");
            return userDTO;
        }

        var user = User.fromDTO(userDTO);
        var book = Book.fromDTO(bookDTO);

        var favourite = new Favourite(book, user);
        var userFavourites = user.getFavourites();
        userFavourites.add(favourite);
        user.setFavourites(userFavourites);

        var userSaved = userRepository.save(user);

        System.out.println("Book correctly assigned");
        showById(userSaved.getId());

        return UserDTO.fromEntity(userSaved);
    }

    private boolean hasFavouriteSaved(UserDTO user, BookDTO book) {
var size = user.getFavourites().size();
        for (FavouriteDTO favourite: user.getFavourites()) {

            if (favourite.getItem().getId() == book.getId()) {
                return true;
            }
        }

        return false;
    }
}
