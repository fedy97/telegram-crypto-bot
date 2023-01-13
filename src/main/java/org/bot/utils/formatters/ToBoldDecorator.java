package org.bot.utils.formatters;

public class ToBoldDecorator extends StringDecorator {

    public ToBoldDecorator(String decoratedString) {
        super(decoratedString);
    }

    @Override
    public String toString() {
        return "*" + decoratedString + "*";
    }
}
