package org.bot.utils.fetchers;

import java.util.List;

/**
 * Strategy pattern, cache or repository
 * @param <T> entity to fetch
 */
public interface DataFetcher<T> {
    List<T> fetchAll();
}
