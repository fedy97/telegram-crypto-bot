package org.bot.operations;

import java.util.ArrayList;
import java.util.List;

public interface Operations {
    void withdraw(String ticker, Double amount, String chain, String address);
    boolean isUsable();
    void build();
    String platform();
    default List<String> getAvailableChains(String ticker) {
        return new ArrayList<>();
    }
}
