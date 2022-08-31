package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.commander.Command;
import com.ironhack.bookmark_app.commander.Commander;
import com.ironhack.bookmark_app.enums.CommandType;
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
                new Command<> ("find :text", CommandType.FIND)
        });

        // Run event when a command is executed
        commander.setAutorun(true);

        do {
            var command = commander.askForCommand();
            if (command.getResult() == CommandType.EXIT) {
                System.out.println("The application has been closed\n");
                break;
            }
            if(command.getResult() == CommandType.FIND) {
                String title = command.getParameter("text");
                System.out.println("How many results you want?");
                int limit = new Scanner(System.in).nextInt();
                try {

                    var toShow = apiService.findByTitle(title, limit);
                    for(Book i:toShow){
                        System.out.println(i);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        } while (true);
    }
}
