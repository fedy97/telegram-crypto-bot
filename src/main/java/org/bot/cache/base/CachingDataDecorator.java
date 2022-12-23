package org.bot.cache.base;

import org.bot.utils.fetchers.base.DataFetcher;
import org.bot.utils.fetchers.base.DataFetcherDecorator;

import java.util.List;

public class CachingDataDecorator<T> extends DataFetcherDecorator<T> {
    private final Cache<T> cache;

    public CachingDataDecorator(DataFetcher<T> fetcher, Cache<T> cache) {
        super(fetcher);
        this.cache = cache;
    }
    @Override
    public List<T> fetchAll() {
        // delegate fetching to subclass
        List<T> toCache = getDataFetcher().fetchAll();
        // added behaviour of the decorator
        updateCache(toCache);
        return toCache;
    }

    private void updateCache(List<T> toCache) {
        this.cache.saveAll(toCache);
    }

}
