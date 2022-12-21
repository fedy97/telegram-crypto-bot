package org.bot.repositories;

import java.util.List;

public interface Repository<T> {
    void save(T t);
    List<T> findAll();

    void deleteByValue(String column, String val);

    void deleteAll();
}
