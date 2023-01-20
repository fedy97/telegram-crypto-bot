package org.bot.repositories.base;

import org.bot.utils.Store;

public interface Repository<T> extends Store<T> {
    String getCollectionName();
}
