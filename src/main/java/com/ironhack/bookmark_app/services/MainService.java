package com.ironhack.bookmark_app.services;

import com.ironhack.bookmark_app.commander.Command;
import com.ironhack.bookmark_app.commander.Commander;
import com.ironhack.bookmark_app.enums.CommandType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @EventListener(ApplicationReadyEvent.class)
    public void MainService() {

        System.out.println("Hello, welcome to the Bookmark Application!");
        System.out.println("Type 'help' to see the available commands");

        final var commander = new Commander<CommandType>(new Command[] {

                // General commands
                new Command<>("exit", CommandType.EXIT),
                new Command<>("help", CommandType.HELP).addOnRun((cr) -> {
                    System.out.println("\nAvailable commands:");
                    System.out.println("\t1. exit - Exit the application");
                }),
        });

        // Run event when a command is executed
        commander.setAutorun(true);

        do {
            var command = commander.askForCommand();
            if(command.getResult() == CommandType.EXIT) {
                System.out.println("The application has been closed\n");
                break;
            }
        } while (true);
    }
}
