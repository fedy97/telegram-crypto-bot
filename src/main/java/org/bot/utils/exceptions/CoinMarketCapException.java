package org.bot.utils.exceptions;

public class CoinMarketCapException extends RuntimeException{
    public CoinMarketCapException() {
        super("CoinMarketCap error. Please open an issue here https://github.com/fedy97/telegram-crypto-bot/issues/new");
    }
}
