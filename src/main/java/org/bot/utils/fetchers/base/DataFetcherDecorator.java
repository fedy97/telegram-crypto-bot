package org.bot.utils.fetchers.base;

import lombok.Getter;

public abstract class DataFetcherDecorator<T> implements DataFetcher<T> {
    @Getter
    private final DataFetcher<T> dataFetcher;

    protected DataFetcherDecorator(DataFetcher<T> dataFetcher) {
        this.dataFetcher = dataFetcher;
    }
}
