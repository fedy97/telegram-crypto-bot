package org.bot.cache;

import org.bot.utils.Data;

import java.util.List;

public interface Cache<T> extends Data<T> {
    boolean isEmpty();

    void saveAll(List<T> toCache);
}
