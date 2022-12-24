package org.bot.utils;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException() {
        super("Invalid command request");
    }
}
