package org.bot.repositories.base;

import org.bot.utils.Data;

public interface Repository<T> extends Data<T> {
    String getCollectionName();
}
