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
                .header("Host", "www.coingecko.com")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:122.0) Gecko/20100101 Firefox/122.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Alt-Used", "www.coingecko.com")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "cross-site")
                .header("Pragma", "no-cache")
                .header("Cache-Control", "no-cache")
                .header("TE", "trailers")
                .get()
                .build();
    }
}
