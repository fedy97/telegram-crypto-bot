package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.base.Cache;
import org.bot.models.Coin;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.repositories.CoinRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinCacheProxy extends Cache<Coin> implements Observer<Coin> {

    private static CoinCacheProxy instance;

    // Private constructor to prevent external instantiation
    private CoinCacheProxy() {
        this.cacheStore = new ArrayList<>();
    }

    // Public static method to get the instance of the cache object
    public static CoinCacheProxy getInstance() {
        if (instance == null) {
            instance = new CoinCacheProxy();
        }
        return instance;
    }

    @Override
    public void update(Action<Coin> action) {
        action.updateData(this);
    }

    @Override
    public List<Coin> findAll() {
        if (isEmpty()) {
            // fetch from Repository and put it in cache if cache is empty
            List<Coin> coins = CoinRepository.getInstance().findAll();
            this.cacheStore.addAll(coins);
        }
        // then return the cache
        return this.cacheStore;
    }
}
