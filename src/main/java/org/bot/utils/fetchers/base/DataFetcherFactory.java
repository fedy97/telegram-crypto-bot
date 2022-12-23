package org.bot.utils.fetchers.base;

public interface DataFetcherFactory<T> {
    DataFetcher<T> createDataFetcher();
}
