package org.bot.cache.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.Data;

import java.lang.reflect.Field;
import java.util.List;
@Slf4j
public abstract class Cache<T> implements Data<T> {

    protected List<T> cacheStore;
    public boolean isEmpty() {
        return this.cacheStore.isEmpty();
    }

    public void save(T entity) {
        this.cacheStore.add(entity);
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
