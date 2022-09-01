package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.commander.Command;
import com.ironhack.bookmark_app.commander.Commander;
import com.ironhack.bookmark_app.enums.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import com.ironhack.bookmark_app.model.Book;
import com.ironhack.bookmark_app.userinput.UserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MainService {

    @Autowired
    UserService userService;



    @Autowired
    ApiService apiService;

    @EventListener(ApplicationReadyEvent.class)
    public void MainService() throws Exception {

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
                        var toShow = apiService.findByTitle();
                        for(Book i:toShow){
                            System.out.println(i);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
