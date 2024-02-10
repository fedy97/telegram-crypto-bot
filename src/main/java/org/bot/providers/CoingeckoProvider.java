package org.bot.providers;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.client.HttpResponseException;
import org.bot.models.Coin;
import org.bot.models.Portfolio;
import org.bot.models.Trending;
import org.bot.utils.exceptions.CoingeckoException;

import java.util.*;

@Slf4j
public class CoingeckoProvider implements DataProvider {

    private static CoingeckoProvider instance;

    private CoingeckoProvider() {
        // Private constructor to prevent instantiation
    }

    public static CoingeckoProvider getInstance() {
        if (instance == null) {
            instance = new CoingeckoProvider();
        }
        return instance;
    }

    public Portfolio getPortfolio(String url) {
        Map<String, Coin> coins = new LinkedHashMap<>();
        Response response = null;
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = this.buildGetRequest(url);
            response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new HttpResponseException(response.code(), response.toString());
            assert response.body() != null;
            String responseBody = response.body().string();
            String[] coinsRaw = responseBody.split("<img loading=\"lazy\" alt=\"");
            for (int i = 1; i < coinsRaw.length - 2; i = i + 2) {
                Coin coin = this.fromRawCoin(coinsRaw[i]);
                if (coin.getTicker() != null) coins.put(coin.getTicker().toUpperCase(), coin);
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error(e.toString());
            throw new CoingeckoException();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new Portfolio(coins);
    }

    public Trending getTrendingCoins() {
        String url = "https://api.coingecko.com/api/v3/search/trending";
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        try {
            Request request = this.buildGetRequest(url);
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                String responseBodyString = responseBody.string();
                return new Trending(responseBodyString);
            }
            log.error("Error in fetching resource");
            throw new CoingeckoException();
        } catch (Exception e) {
            log.error("Error in fetching resource", e);
            throw new CoingeckoException();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public Request buildGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .header("Upgrade-Insecure-Requests", "1")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "cross-site")
                .get()
                .build();
    }

    @Override
    public Coin fromRawCoin(String raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.split(" \\(")[0]);
            coin.setTicker(raw.split("\\(")[1].split("\\)")[0]);
            coin.setLink("https://www.coingecko.com" + raw.split("\"width: 115px;\" href=\"")[1].split("\"")[0]);
            coin.setPrice(Double.parseDouble(raw.split("<td data-sort=\"")[1].split("\"")[0]));
            coin.setChange24(raw.split("data-formatted=\"false\">")[2].split("<")[0]);
        } catch (Exception e) {
            log.warn("some info for token ignored: " + e.getMessage());
        }
        return coin;
    }
}
