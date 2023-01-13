package org.bot.utils.formatters;

public class ToItalicDecorator extends StringDecorator {

    public ToItalicDecorator(String decoratedString) {
        super(decoratedString);
    }

    @Override
    public String toString() {
        return "_" + decoratedString + "_";
    }
}
