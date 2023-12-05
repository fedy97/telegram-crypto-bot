package org.bot.utils.exceptions;

public class CommandExecutionException extends RuntimeException{
    public CommandExecutionException(String message) {
        super("Command execution error: " + message);
    }
}
