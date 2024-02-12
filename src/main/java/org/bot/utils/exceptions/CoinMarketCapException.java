package org.bot.utils.exceptions;

public class CoinMarketCapException extends RuntimeException{
    public CoinMarketCapException() {
        super("CoinMarketCap error");
    }
}
