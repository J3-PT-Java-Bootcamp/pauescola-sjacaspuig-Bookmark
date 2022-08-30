package com.ironhack.bookmark_app.error;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Entity not found");
    }
}