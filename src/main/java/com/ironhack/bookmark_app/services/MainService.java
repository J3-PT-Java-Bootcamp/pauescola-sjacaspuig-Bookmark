package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.commander.Command;
import com.ironhack.bookmark_app.commander.Commander;
import com.ironhack.bookmark_app.enums.CommandType;
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

    @EventListener(ApplicationReadyEvent.class)
    public void MainService() {

        System.out.println("Hello, welcome to the Bookmark Application!");
        System.out.println("Type 'help' to see the available commands");

        final var commander = new Commander<CommandType>(new Command[]{

                // General commands
                new Command<>("exit", CommandType.EXIT),
                new Command<>("help", CommandType.HELP).addOnRun((cr) -> {
                    System.out.println("\nAvailable commands:");
                    System.out.println("\t1. exit - Exit the application");
                }),
                new Command<>("new user", CommandType.NEW_USER).addOnRun((cr) -> {
                    userService.createUser();
                }),
                new Command<>("lookup user :id", CommandType.LOOKUP_USER).addOnRun((cr) -> {
                    userService.showById(cr.getLongParameter("id"));
                }),
                new Command<>("show users", CommandType.SHOW_USERS).addOnRun((cr) -> {
                    userService.showAll();
                }),
                new Command<>("search book", CommandType.SEARCH_BOOK).addOnRun((cr) -> {
                    try {
                        apiService.searchBook();
                    } catch (ParseException e) {
                        throw new RuntimeException("Book not found");
                    }
                }),
                new Command<>("show books", CommandType.SHOW_BOOKS).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report users by book", CommandType.USERS_BY_BOOK).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report users to read by book", CommandType.USERS_TO_READ_BY_BOOK).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report users reading by book", CommandType.USERS_READING_BY_BOOK).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report users read by user", CommandType.USERS_READ_BY_BOOK).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report books by user", CommandType.BOOKS_BY_USER).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report books to read by user", CommandType.BOOKS_TO_READ_BY_USER).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report books reading by user", CommandType.BOOKS_READING_BY_USER).addOnRun((cr) -> {
                    bookService.showAll();
                }),
                new Command<>("report books read by user", CommandType.BOOKS_READ_BY_USER).addOnRun((cr) -> {
                    bookService.showAll();
                }),
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
