package org.bot.models;

import org.bot.cache.CacheFlyWeight;
import org.bot.repositories.CoinRepository;
import org.bot.utils.NumberOperations;

import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {

    private final List<Coin> coins;
    private final Map<String, Double> buyPrices;
    private final StringBuilder sb;

    public Portfolio(List<Coin> coins) {
        this.coins = coins;
        List<Coin> bp = CacheFlyWeight.getInstance(Coin.class, CoinRepository.getInstance()).findAll();
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
                coin.setMultiplier(NumberOperations.roundFloat(coin.getPrice() / buyPriceCoin, 1) + "x");
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

    public void sort() {
        // find coins with empty multiplier
        List<Coin> excludedCoins = coins.stream()
                .filter(coin -> coin.getMultiplier().isEmpty())
                .collect(Collectors.toList());
        coins.removeAll(excludedCoins);
        // sort coins by multiplier value
        coins.sort((c1, c2) -> c2.getMultiplier().compareTo(c1.getMultiplier()));
        // put at the end of the list the excluded coins
        coins.addAll(excludedCoins);
    }
}
