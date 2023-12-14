package org.bot.utils.formatters;

import java.util.Arrays;

public class BalanceDecorator extends StringDecorator {

    public BalanceDecorator(String decoratedString) {
        super(decoratedString);
    }

    @Override
    public String toString() {
        String noCurlyBraces = decoratedString.replace("{", "").replace("}", "");
        String[] coins = noCurlyBraces.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coins.length; i++) {
            String coin = coins[i].trim();
            sb.append(coin.replace("=", ": "));
            if (i < coins.length - 1)
                sb.append("\n");
        }
        return sb.toString();
    }
}
