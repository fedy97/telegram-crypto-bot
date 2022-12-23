package org.bot.utils;

import java.util.List;

public interface Data<T> {
    String getCollectionName();
    void save(T t);
    List<T> findAll();

    void deleteByValue(String column, String val);

    void deleteAll();
}
