package com.ironhack.bookmark_app.commander;

import java.util.Map;

public class CommandResult<T> {
    private Map<String, String> parameters;
    private Command<T> command;

    public CommandResult(Map<String, String> parameters, Command command) {
        this.parameters = parameters;
        this.command = command;
    }

    public T getResult() {
        return command.getValue();
    }


    public Map<String, String> getParameters() {
        return parameters;
    }

    public void run() {
        this.command.run(this);
    }

    public Long getLongParameter(String key) {
        return Long.parseLong((getParameters().get(key)));
    }
}