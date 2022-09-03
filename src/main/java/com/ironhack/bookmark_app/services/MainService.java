package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.commander.Command;
import com.ironhack.bookmark_app.commander.Commander;
import com.ironhack.bookmark_app.enums.CommandType;
import com.ironhack.bookmark_app.error.NotFoundException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Autowired
    UserService userService;

    @Autowired
    ApiService apiService;

    @Autowired
    BookService bookService;

    @Autowired
    FavouriteService favouriteService;

    @EventListener(ApplicationReadyEvent.class)
    public void MainService() {

        System.out.println("Hello, welcome to the Bookmark Application!");
        System.out.println("Type 'help' to see the available commands");

        final var commander = new Commander<CommandType>(new Command[]{

                // General commands
                new Command<>("exit", CommandType.EXIT),
                new Command<>("help", CommandType.HELP).addOnRun((cr) -> {
                    System.out.println("\nAvailable commands:");
                    System.out.println("\t1. new user");
                    System.out.println("\t2. lookup user :id");
                    System.out.println("\t3. show users");
                    System.out.println("\t4. search book");
                    System.out.println("\t5. show books");
                    System.out.println("\t6. lookup book :id");
                    System.out.println("\t7. assign book :bookId to user :userId");
                    System.out.println("\t8. status user books :id");
                }),

                // USER
                new Command<>("new user", CommandType.NEW_USER).addOnRun((cr) -> {
                    userService.createUser();
                }),
                new Command<>("lookup user :id", CommandType.LOOKUP_USER).addOnRun((cr) -> {
                    userService.showById(cr.getLongParameter("id"));
                }),
                new Command<>("show users", CommandType.SHOW_USERS).addOnRun((cr) -> {
                    userService.showAll();
                }),

                // BOOK
                new Command<>("search book", CommandType.SEARCH_BOOK).addOnRun((cr) -> {
                    try {
                        apiService.searchBook();
                    } catch (ParseException | NotFoundException e) {
                        throw new RuntimeException("Book not found");
                    }
                }),
                new Command<>("show books", CommandType.SHOW_BOOKS).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("lookup book :id", CommandType.LOOKUP_BOOK).addOnRun((cr) -> {
                    bookService.showById(cr.getParameter("id"));
                }),

                // BOOK TO USER
                new Command<>("assign book :bookId to user :userId", CommandType.ASSIGN_BOOK_TO_USER).addOnRun((cr) -> {
                    try {
                        userService.assignBook(cr.getLongParameter("userId"), cr.getParameter("bookId"));
                    } catch (NotFoundException e) {
                        throw new RuntimeException("The book was not assigned to the user. An error occurred.");
                    }
                }),

                // FAVOURITES
                new Command<>("status user books :id", CommandType.STATUS_BOOKS_USER).addOnRun((cr) -> {
                    try {
                        favouriteService.updateStatus(cr.getLongParameter("id"));
                    } catch (NotFoundException e) {
                        throw new RuntimeException("The status could not be changed. An error has occurred.");
                    }
                })
        });

        // Run event when a command is executed
        commander.setAutorun(true);

        do {
            var command = commander.askForCommand();
            if (command.getResult() == CommandType.EXIT) {
                System.out.println("The application has been closed\n");
                break;
            }

        } while (true);
    }
}
