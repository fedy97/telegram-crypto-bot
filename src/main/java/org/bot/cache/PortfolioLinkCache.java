package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.base.Cache;
import org.bot.models.PortfolioLink;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.utils.fetchers.base.DataFetcher;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PortfolioLinkCache extends Cache<PortfolioLink> implements DataFetcher<PortfolioLink>, Observer<PortfolioLink> {

    private static PortfolioLinkCache instance;

    // Private constructor to prevent external instantiation
    private PortfolioLinkCache() {
        this.cacheStore = new ArrayList<>();
    }

    // Public static method to get the instance of the cache object
    public static PortfolioLinkCache getInstance() {
        if (instance == null) {
            instance = new PortfolioLinkCache();
        }
        return instance;
    }

    @Override
    public List<PortfolioLink> fetchAll() {
        return findAll();
    }

    @Override
    public void update(Action<PortfolioLink> action) {
        action.updateData(this);
    }
}
