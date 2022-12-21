package org.bot.utils;

import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCache {
    // Static instance of the cache object
    private static QueryCache instance;
    // Map to store the cached collection results
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

    // Method to add a collection result to the cache
    public void addToCache(String collection, List<Document> result) {
        cache.put(collection, result);
    }

    // Method to check if a collection is present in the cache
    public boolean isCached(String collection) {
        return cache.containsKey(collection);
    }

    public void invalidateCache(String collection) {
        cache.remove(collection);
    }

    public void addElementToListCached(String collection, Document document) {
        if (cache.containsKey(collection)) {
            List<Document> documents = cache.get(collection);
            documents.add(document);
        }
    }

    public void deleteElementToListCached(String collection, String col, String ticker) {
        if (cache.containsKey(collection)) {
            List<Document> documents = cache.get(collection);
            for (int i = 0; i < documents.size(); i++) {
                if (documents.get(i).getString(col).equals(ticker)) {
                    documents.remove(i);
                    break;
                }
            }
        }
    }

    // Method to get a cached collection result
    public List<Document> getCachedResult(String collection) {
        return cache.get(collection);
    }
}
