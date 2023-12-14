package org.bot.operations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Operations {
    void withdraw(String ticker, Double amount, String chain, String address);
    boolean isUsable();
    void build();
    String platform();
    Set<String> getAvailableChains(String ticker);
    Map<String, Double> getBalance();
}
