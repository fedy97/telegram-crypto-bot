package org.bot.cache;

import lombok.extern.slf4j.Slf4j;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.utils.Data;
import org.bot.utils.exceptions.InvalidCommandException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CacheFlyWeight<T> implements Data<T>, Observer<T> {
    private static final Map<Class<?>, CacheFlyWeight<?>> cacheMap = new HashMap<>();
    private final List<T> cacheStore;

    private final Data<T> repository;

    private CacheFlyWeight(Data<T> repository) {
        this.repository = repository;
        this.cacheStore = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public static <T> CacheFlyWeight<T> getInstance(Class<T> type, Data<T> repository) {
        return (CacheFlyWeight<T>) cacheMap.computeIfAbsent(type, t -> new CacheFlyWeight<>(repository));
    }

    public boolean isEmpty() {
        return this.cacheStore.isEmpty();
    }

    public void save(T entity) {
        this.cacheStore.add(entity);
    }

    @Override
    public List<T> findAll() {
        // proxy pattern
        if (isEmpty()) {
            // fetch from Repository and put it in cache if cache is empty
            List<T> coins = this.repository.findAll();
            this.cacheStore.addAll(coins);
        }
        // then return the cache
        return this.cacheStore;
    }

    public void deleteByValue(String column, String val) {
        for (int i = 0; i < cacheStore.size(); i++) {
            T entity = this.cacheStore.get(i);
            Class<?> cls = entity.getClass();
            try {
                Field field = cls.getDeclaredField(column);
                field.setAccessible(true);
                Object attributeValue = field.get(entity);
                if (attributeValue.equals(val)) {
                    this.cacheStore.remove(i);
                    break;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new InvalidCommandException();
            }
        }
    }

    public void deleteAll() {
        this.cacheStore.clear();
    }

    @Override
    public void update(Action<T> action) {
        action.updateData(this);
    }
}
