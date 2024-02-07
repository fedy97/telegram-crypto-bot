package org.bot.utils;

import okhttp3.Request;

import java.util.Map;

public class Helpers {
    private Helpers() {
    }

    public static void addToMapOrSum(Map<String, Double> map, String key, Double value) {
        map.compute(key, (k, v) -> (v == null) ? value : v + value);
    }

    public static Request buildRequestCoingecko(String url) {
        return new Request.Builder()
                .url(url)
                .header("Upgrade-Insecure-Requests", "1")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "cross-site")
                .get()
                .build();
    }
}
