package org.bot.operations;

import java.util.Map;
import java.util.Set;

public interface Operations {

    /**
     * @param ticker to withdraw
     * @param amount to withdraw, ex. 1 means 1 <ticker>
     * @param chain id for the specified ticker, ex. bep20
     * @param address to receive funds
     */
    void withdraw(String ticker, Double amount, String chain, String address);
    void trade(String action, String ticker, String type, Double amount, Double price);

    /**
     * this checks if all the required values are present before building the operations object
     * @return true if all required values are provided.
     */
    boolean isUsable();

    /**
     * builder of the concrete operations object
     */
    void build();

    /**
     * @return the platform (exchange or evm) for the implemented operations (ex. kucoin)
     */
    String platform();

    /**
     * @param ticker to fetch chains
     * @return a set of chains as ids (ex. bep20, erc20)
     */
    Set<String> getAvailableChains(String ticker);

    /**
     * @return a map with key the ticker (ex. BTC) and value the quantity
     */
    Map<String, Double> getBalance();
}
