package org.bot.utils.formatters;

public abstract class StringDecorator {
    protected String decoratedString;

    protected StringDecorator(String decoratedString) {
        this.decoratedString = decoratedString;
    }

    public abstract String toString();
}
