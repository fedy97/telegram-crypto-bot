package org.bot.utils.exceptions;

public class InvalidCommandException extends RuntimeException{
    public InvalidCommandException() {
        super("Invalid command request");
    }
}
