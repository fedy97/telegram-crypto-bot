package org.bot.utils.fetchers;

public interface DataFetcherFactory<T> {
    DataFetcher<T> createDataFetcher();
}
