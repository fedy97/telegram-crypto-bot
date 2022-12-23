package org.bot.utils.fetchers;

import org.bot.cache.CachingDataDecorator;
import org.bot.cache.CoinCache;
import org.bot.models.Coin;
import org.bot.repositories.CoinRepository;

public class CoinFetcherFactory implements DataFetcherFactory<Coin> {

    private static CoinFetcherFactory instance;

    // Private constructor to prevent external instantiation
    private CoinFetcherFactory() {

    }

    // Public static method to get the instance of the cache object
    public static CoinFetcherFactory getInstance() {
        if (instance == null) {
            instance = new CoinFetcherFactory();
        }
        return instance;
    }

    @Override
    public DataFetcher<Coin> createDataFetcher() {
        // find the right strategy here and return it
        DataFetcher<Coin> strategy;
        CoinCache coinCache = CoinCache.getInstance();
        if (coinCache.isEmpty())
            strategy = new CachingDataDecorator<>(CoinRepository.getInstance(), coinCache);
        else
            strategy = coinCache;
        return strategy;
    }
}
