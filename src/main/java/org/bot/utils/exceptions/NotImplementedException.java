package org.bot.utils.exceptions;

public class NotImplementedException extends CommandExecutionException{
    public NotImplementedException() {
        super("not implemented");
    }
}
