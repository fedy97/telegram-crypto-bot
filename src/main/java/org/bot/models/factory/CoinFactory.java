package org.bot.models.factory;

import lombok.extern.slf4j.Slf4j;
import org.bot.models.Coin;

@Slf4j
public class CoinFactory {

    private CoinFactory() {

    }

    public static Coin fromRawCoin(String raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.split(" \\(")[0]);
            coin.setTicker(raw.split("\\(")[1].split("\\)")[0]);
            coin.setLink("https://www.coingecko.com" + raw.split("\"width: 115px;\" href=\"")[1].split("\"")[0]);
            coin.setPrice(Double.parseDouble(raw.split("<td data-sort=\"")[1].split("\"")[0]));
            coin.setChange24(raw.split("data-formatted=\"false\">")[2].split("<")[0]);
        } catch (Exception e) {
            log.warn("some info for token ignored: " + e.getMessage());
        }
        return coin;
    }
}
