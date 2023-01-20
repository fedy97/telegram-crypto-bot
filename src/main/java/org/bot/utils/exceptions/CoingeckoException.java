package org.bot.utils.exceptions;

public class CoingeckoException extends RuntimeException{
    public CoingeckoException() {
        super("Coingecko error");
    }
}
