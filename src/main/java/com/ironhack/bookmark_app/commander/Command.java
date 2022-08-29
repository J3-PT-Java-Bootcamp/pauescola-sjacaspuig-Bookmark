package com.ironhack.bookmark_app.commander;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Command<T> {
    private String command;
    private String description;
    private final List<Consumer<CommandResult<T>>> onRun = new LinkedList<>();
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Command<T> addOnRun(Consumer<CommandResult<T>> onRun) {
        this.onRun.add(onRun);
        return this;
    }
    public void run(CommandResult<T> cr) {
        for(var onRun: this.onRun) {
            try {
                onRun.accept(cr);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // Exceptions  are stopped here
            }
        }
    }

    public Command(String command, T value) {
        setCommand(command);
        setValue(value);
    }

    public Command(String command, T value, String description) {
        setCommand(command);
        setDescription(description);
        setValue(value);
    }

    // Behaviours

    public CommandResult<T> tryProcessCommand(String command) {

        var com = command.split(" ");
        var originalCom = this.command.split(" ");

        if(com.length != originalCom.length) return null;

        final var parameters = new HashMap<String, String>();

        for(int i = 0; i < com.length; i++) {
            if(originalCom[i].charAt(0) == ':') {
                parameters.put(originalCom[i].substring(1), com[i]);
                continue;
            }
            if(!originalCom[i].equalsIgnoreCase(com[i])) return null;
        }

        return new CommandResult<T>(parameters, this);
    }
}
