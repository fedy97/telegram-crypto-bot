package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.models.Coin;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.utils.Utils;
import org.bot.utils.fetchers.DataFetcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinCache implements Cache<Coin>, DataFetcher<Coin>, Observer<Coin> {

    private static CoinCache instance;

    private final List<Coin> cache;

    // Private constructor to prevent external instantiation
    private CoinCache() {
        cache = new ArrayList<>();
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
        log.info("fetching from cache");
        return findAll();
    }

    @Override
    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    @Override
    public void saveAll(List<Coin> toCache) {
        this.cache.clear();
        this.cache.addAll(toCache);
    }


    @Override
    public String getCollectionName() {
        return Utils.getEnvVar("COINS_COLLECTION");
    }

    @Override
    public void save(Coin coin) {
        this.cache.add(coin);
    }

    @Override
    public List<Coin> findAll() {
        return this.cache;
    }

    @Override
    public void deleteByValue(String column, String val) {
        for (int i = 0; i < cache.size(); i++) {
            Coin entity = this.cache.get(i);
            Class<?> cls = entity.getClass();
            try {
                Field field = cls.getDeclaredField(column);
                field.setAccessible(true);
                Object attributeValue = field.get(entity);
                if (attributeValue.equals(val)) {
                    this.cache.remove(i);
                    break;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception
            }
        }
    }

    @Override
    public void deleteAll() {
        this.cache.clear();
    }

    @Override
    public void update(Action<Coin> action) {
        log.info("notifying cache");
        action.doAction(this);
    }
}
