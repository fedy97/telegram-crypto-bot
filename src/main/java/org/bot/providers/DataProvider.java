package org.bot.providers;

import okhttp3.Request;
import org.bot.models.Coin;
import org.bot.models.Portfolio;
import org.bot.models.Trending;

public interface DataProvider {
    Portfolio getPortfolio(String url);

    Trending getTrendingCoins();

    default Request buildGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    Coin fromRawCoin(String raw);
}
