package org.bot.cache.base;

import org.bot.utils.Data;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Cache<T> implements Data<T> {

    protected List<T> cacheStore;
    public boolean isEmpty() {
        return this.cacheStore.isEmpty();
    }

    public void saveAll(List<T> toCache) {
        this.cacheStore.clear();
        this.cacheStore.addAll(toCache);
    }

    public void save(T coin) {
        this.cacheStore.add(coin);
    }

    public List<T> findAll() {
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
                // Handle the exception
            }
        }
    }

    public void deleteAll() {
        this.cacheStore.clear();
    }
}
