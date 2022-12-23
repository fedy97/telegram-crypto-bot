package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.base.Cache;
import org.bot.models.Coin;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.utils.fetchers.base.DataFetcher;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinCache extends Cache<Coin> implements DataFetcher<Coin>, Observer<Coin> {

    private static CoinCache instance;

    // Private constructor to prevent external instantiation
    private CoinCache() {
        this.cacheStore = new ArrayList<>();
    }

    // Public static method to get the instance of the cache object
    public static CoinCache getInstance() {
        if (instance == null) {
            instance = new CoinCache();
        }
        return instance;
    }

    @Override
    public List<Coin> fetchAll() {
        return findAll();
    }

    @Override
    public void update(Action<Coin> action) {
        action.updateData(this);
    }
}
