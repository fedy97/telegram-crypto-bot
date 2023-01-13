package org.bot.utils.formatters;

public class ToLinkDecorator extends StringDecorator{

    private final String link;

    public ToLinkDecorator(String decoratedString, String link) {
        super(decoratedString);
        this.link = link;
    }

    @Override
    public String toString() {
        return "[" + decoratedString + "]" + "(" + link + ")";
    }
}
