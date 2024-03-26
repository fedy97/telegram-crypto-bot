package org.bot.models;

import lombok.Getter;
import org.bot.cache.CacheFlyWeight;
import org.bot.repositories.CoinRepository;
import org.bot.utils.EnvVars;
import org.bot.utils.NumberOperations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Portfolio {

    @Getter
    private final Map<String, Coin> coins;
    private final Map<String, Double> buyPrices;
    private final StringBuilder sb;
    private final double threshold = Double.parseDouble(EnvVars.getEnvVar("THRESHOLD_MULTIPLIER", "0.5"));

    public Portfolio(Map<String, Coin> coins) {
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
        for (Coin coin : coins.values()) {
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
        for (Coin coin : coins.values()) {
            if (!coin.getMultiplier().isEmpty() && Double.parseDouble(coin.getMultiplier().replace("x", "")) > threshold)
                sb.append(coin);
        }
        return sb.toString();
    }

    public void sort() {
        List<Map.Entry<String, Coin>> entryList = coins.entrySet().stream().sorted(Comparator.comparingDouble((Map.Entry<String, Coin> coin) -> {
            if (coin.getValue().getMultiplier().isEmpty())
                return Double.NEGATIVE_INFINITY;
            else
                return Double.parseDouble(coin.getValue().getMultiplier().replace("x", ""));
        }).reversed()).collect(Collectors.toList());
        coins.clear();
        for (Map.Entry<String, Coin> entry : entryList)
            coins.put(entry.getKey(), entry.getValue());
    }
}
