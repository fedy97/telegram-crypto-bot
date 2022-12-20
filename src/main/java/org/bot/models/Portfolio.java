package org.bot.models;

import java.util.List;

public class Portfolio {

    private final List<Coin> coins;
    private final StringBuilder sb;

    public Portfolio(List<Coin> coins) {
        this.coins = coins;
        this.sb = new StringBuilder();
    }

    public List<Coin> getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        if (sb.length() > 0)
            sb.setLength(0);
        for (Coin coin : coins) {
            sb.append(coin.toString());
        }
        return sb.toString();
    }
}
