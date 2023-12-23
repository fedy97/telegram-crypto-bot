package org.bot.utils.formatters;

import java.util.Map;

public class ChainsDecorator extends MapDecorator<String, Double> {

    public ChainsDecorator(Map<String, Double> decoratedMap) {
        super(decoratedMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : decoratedMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" (withdrawal fee: ");
            sb.append(entry.getValue());
            sb.append(")\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
