package org.bot.utils.formatters;

public class ToCodeDecorator extends StringDecorator {

    public ToCodeDecorator(String decoratedString) {
        super(decoratedString);
    }

    @Override
    public String toString() {
        return "`" + decoratedString + "`";
    }
}
