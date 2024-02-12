package org.bot.providers;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.bot.models.Coin;
import org.bot.models.Portfolio;
import org.bot.models.Trending;
import org.bot.utils.exceptions.NotImplementedException;

public interface DataProvider {
    Portfolio getPortfolio(String url);

    Trending getTrendingCoins();

    default Request buildGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .header("Cache-Control", "no-cache")
                .build();
    }

    default Request buildPostRequest(String url, String requestBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody);
        return new Request.Builder()
                .url(url)
                .post(body)
                .header("Cache-Control", "no-cache")
                .build();
    }

    default Coin fromRawCoin(String raw) {
        throw new NotImplementedException();
    }

    default Coin fromRawCoin(JsonNode raw) {
        throw new NotImplementedException();
    }
}
