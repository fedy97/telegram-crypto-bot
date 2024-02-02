package org.bot.utils.exceptions;

public class CoingeckoException extends RuntimeException{
    public CoingeckoException() {
        super("Coingecko error. Please open an issue here https://github.com/fedy97/telegram-crypto-bot/issues/new");
    }
}
