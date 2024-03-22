package org.bot.operations;

import org.bot.utils.exceptions.NotImplementedException;

import java.util.Map;

public interface Operations {

    /**
     * @param ticker  to withdraw
     * @param amount  to withdraw, ex. 1 means 1 <ticker>
     * @param chain   id for the specified ticker, ex. bep20
     * @param address to receive funds
     */
    default void withdraw(String ticker, Double amount, String chain, String address) {
        throw new NotImplementedException();
    }

    /**
     * @param ticker  to withdraw
     */
    default Map<String, String> deposit(String ticker, String chain) {
        throw new NotImplementedException();
    }

    /**
     * trade a coin
     * @param action buy/sell
     * @param ticker to buy/sell
     * @param type market/limit
     * @param amount of the coin to buy/sell
     * @param price optional, for limit orders
     * @return order id
     */
    default String trade(String action, String ticker, String type, Double amount, Double price) {
        throw new NotImplementedException();
    }

    /**
     * this checks if all the required values are present before building the operations object
     *
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
     * @return a map of chains as keys (ex. bep20, erc20) and withdrawal fee as value
     */
    default Map<String, Double> getAvailableChains(String ticker) {
        throw new NotImplementedException();
    }

    /**
     * @return a map with key the ticker (ex. BTC) and value the quantity
     */
    default Map<String, Double> getBalance() {
        throw new NotImplementedException();
    }
}
