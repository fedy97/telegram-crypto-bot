package org.bot.utils;

public class Validator {

    private Validator() {

    }
    public static boolean validCoingeckoLink(String link) {
        return link.contains("coingecko.com/it/portfolios/public/");
    }
}
