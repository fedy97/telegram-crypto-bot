package org.bot.repositories;

import java.util.List;

public interface Repository<T> {
    void save(T t);
    T findById(String id);
    List<T> findAll();
    void deleteById(String id);

    void deleteByValue(String column, String val);

    void deleteAll();
}
