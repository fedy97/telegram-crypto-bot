package org.bot.models;

import org.bot.repositories.MongoCoinRepository;
import org.bot.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Portfolio {

    private final List<Coin> coins;
    private final Map<String, Double> buyPrices;
    private final StringBuilder sb;

    public Portfolio(List<Coin> coins) {
        this.coins = coins;
        List<Coin> bp = MongoCoinRepository.getInstance().findAll();
        this.buyPrices = bp.stream()
                .collect(Collectors.toMap(
                        Coin::getTicker,
                        Coin::getPrice,
                        (oldValue, newValue) -> oldValue, // discard newValue in collisions
                        HashMap::new
                ));
        addMultipliers();
        this.sb = new StringBuilder();
    }

    private void addMultipliers() {
        for (Coin coin : coins) {
            if (buyPrices.containsKey(coin.getTicker())) {
                Double buyPriceCoin = buyPrices.get(coin.getTicker());
                coin.setMultiplier(Utils.roundFloat(coin.getPrice() / buyPriceCoin, 1) + "x");
            }
        }
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
