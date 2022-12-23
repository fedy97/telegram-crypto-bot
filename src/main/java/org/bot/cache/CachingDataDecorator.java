package org.bot.cache;

import org.bot.utils.fetchers.DataFetcher;

import java.util.List;

public class CachingDataDecorator<T> implements DataFetcher<T> {

    private final DataFetcher<T> fetcher;
    private final Cache<T> cache;

    public CachingDataDecorator(DataFetcher<T> fetcher, Cache<T> cache) {
        this.fetcher = fetcher;
        this.cache = cache;
    }
    @Override
    public List<T> fetchAll() {
        // delegate fetching to subclass
        List<T> toCache = this.fetcher.fetchAll();
        // added behaviour of the decorator
        updateCache(toCache);
        return toCache;
    }

    private void updateCache(List<T> toCache) {
        this.cache.saveAll(toCache);
    }

}
