package org.bot.models.factory;

import org.bot.models.Coin;

public class CoinFactory {

    private CoinFactory() {

    }

    public static Coin fromRawCoin(String raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.split(" ")[0]);
            coin.setTicker(raw.split("\\(")[1].split("\\)")[0]);
            coin.setLink("https://www.coingecko.com" + raw.split("\"width: 115px;\" href=\"")[1].split("\"")[0]);
            coin.setPrice(Double.parseDouble(raw.split("<td data-sort=\"")[1].split("\"")[0]));
            coin.setChange24(raw.split("data-show-solid-arrow=\"false\" data-formatted=\"false\">")[2].split("<")[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return coin;
    }
}
