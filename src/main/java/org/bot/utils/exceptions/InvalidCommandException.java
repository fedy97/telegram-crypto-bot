package org.bot.utils.exceptions;

public class InvalidCommandException extends RuntimeException{
    private static final String DEFAULT_INVALID_MESSAGE = "Invalid command format";
    public InvalidCommandException() {
        super(DEFAULT_INVALID_MESSAGE);
    }

    public InvalidCommandException(String optionalErrorMessage) {
        super(DEFAULT_INVALID_MESSAGE + ". " + optionalErrorMessage);
    }
}
