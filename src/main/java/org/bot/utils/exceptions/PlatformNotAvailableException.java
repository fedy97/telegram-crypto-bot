package org.bot.utils.exceptions;

public class PlatformNotAvailableException extends RuntimeException{
    public PlatformNotAvailableException() {
        super("Platform not available");
    }
}
