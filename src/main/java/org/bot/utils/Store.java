package org.bot.utils;

import java.util.List;

public interface Store<T> {
    void save(T t);
    List<T> findAll();

    void deleteByValue(String column, String val);

    void deleteAll();
}
