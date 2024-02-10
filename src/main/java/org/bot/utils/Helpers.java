package org.bot.utils;

import java.util.Map;

public class Helpers {
    private Helpers() {
    }

    public static void addToMapOrSum(Map<String, Double> map, String key, Double value) {
        map.compute(key, (k, v) -> (v == null) ? value : v + value);
    }
}
