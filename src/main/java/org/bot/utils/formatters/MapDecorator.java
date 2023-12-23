package org.bot.utils.formatters;

import java.util.Map;

public abstract class MapDecorator<K, V> {
    protected Map<K, V> decoratedMap;

    protected MapDecorator(Map<K, V> decoratedMap) {
        this.decoratedMap = decoratedMap;
    }

    public abstract String toString();
}
