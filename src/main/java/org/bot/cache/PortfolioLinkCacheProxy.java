package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.base.Cache;
import org.bot.models.PortfolioLink;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.repositories.PortfolioLinkRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PortfolioLinkCacheProxy extends Cache<PortfolioLink> implements Observer<PortfolioLink> {

    private static PortfolioLinkCacheProxy instance;

    // Private constructor to prevent external instantiation
    private PortfolioLinkCacheProxy() {
        this.cacheStore = new ArrayList<>();
    }

    // Public static method to get the instance of the cache object
    public static PortfolioLinkCacheProxy getInstance() {
        if (instance == null) {
            instance = new PortfolioLinkCacheProxy();
        }
        return instance;
    }

    @Override
    public void update(Action<PortfolioLink> action) {
        action.updateData(this);
    }

    @Override
    public List<PortfolioLink> findAll() {
        if (isEmpty()) {
            // fetch from Repository and put it in cache if cache is empty
            List<PortfolioLink> portfolioLinks = PortfolioLinkRepository.getInstance().findAll();
            this.cacheStore.addAll(portfolioLinks);
        }
        // then return the cache
        return this.cacheStore;
    }
}
