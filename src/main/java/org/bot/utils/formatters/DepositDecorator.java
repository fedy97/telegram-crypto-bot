package org.bot.utils.formatters;

import java.util.Map;

public class DepositDecorator extends MapDecorator<String, String> {

    public DepositDecorator(Map<String, String> decoratedMap) {
        super(decoratedMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : decoratedMap.entrySet()) {
            sb.append(new ToBoldDecorator(entry.getKey()));
            sb.append(": ");
            sb.append(new ToCodeDecorator(entry.getValue()));
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
