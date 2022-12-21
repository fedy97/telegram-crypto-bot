package org.bot.utils;

import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCache {
    // Static instance of the cache object
    private static QueryCache instance;
    // Map to store the cached query results
    private final Map<String, List<Document>> cache;

    // Private constructor to prevent external instantiation
    private QueryCache() {
        cache = new HashMap<>();
    }

    // Public static method to get the instance of the cache object
    public static QueryCache getInstance() {
        if (instance == null) {
            instance = new QueryCache();
        }
        return instance;
    }

    // Method to add a query result to the cache
    public void addToCache(String query, List<Document> result) {
        cache.put(query, result);
    }

    // Method to check if a query is present in the cache
    public boolean isCached(String query) {
        return cache.containsKey(query);
    }

    public void invalidateCache(String query) {
        cache.remove(query);
    }

    public void addElementToListCached(String query, Document document) {
        if (cache.containsKey(query)) {
            List<Document> documents = cache.get(query);
            documents.add(document);
        }
    }

    // Method to get a cached query result
    public List<Document> getCachedResult(String query) {
        return cache.get(query);
    }
}
