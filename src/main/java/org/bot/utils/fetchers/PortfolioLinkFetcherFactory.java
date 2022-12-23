package org.bot.utils.fetchers;

import org.bot.cache.base.CachingDataDecorator;
import org.bot.cache.PortfolioLinkCache;
import org.bot.models.PortfolioLink;
import org.bot.repositories.PortfolioLinkRepository;
import org.bot.utils.fetchers.base.DataFetcher;
import org.bot.utils.fetchers.base.DataFetcherFactory;

public class PortfolioLinkFetcherFactory implements DataFetcherFactory<PortfolioLink> {

    private static PortfolioLinkFetcherFactory instance;

    // Private constructor to prevent external instantiation
    private PortfolioLinkFetcherFactory() {

    }

    // Public static method to get the instance of the cache object
    public static PortfolioLinkFetcherFactory getInstance() {
        if (instance == null) {
            instance = new PortfolioLinkFetcherFactory();
        }
        return instance;
    }

    @Override
    public DataFetcher<PortfolioLink> createDataFetcher() {
        // find the right strategy here and return it
        DataFetcher<PortfolioLink> strategy;
        PortfolioLinkCache portfolioLinkcache = PortfolioLinkCache.getInstance();
        if (portfolioLinkcache.isEmpty())
            // fetch from db and add to cache
            strategy = new CachingDataDecorator<>(PortfolioLinkRepository.getInstance(), portfolioLinkcache);
        else
            // already in cache, return it
            strategy = portfolioLinkcache;
        return strategy;
    }
}
