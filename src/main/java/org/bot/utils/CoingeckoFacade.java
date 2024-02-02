package org.bot.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bot.models.Coin;
import org.bot.models.Portfolio;
import org.bot.models.Trending;
import org.bot.models.factory.CoinFactory;
import org.bot.utils.exceptions.CoingeckoException;

import java.util.*;

@Slf4j
public class CoingeckoFacade {

    private static CoingeckoFacade instance;

    private CoingeckoFacade() {
        // Private constructor to prevent instantiation
    }

    public static CoingeckoFacade getInstance() {
        if (instance == null) {
            instance = new CoingeckoFacade();
        }
        return instance;
    }

    public Portfolio getCoingeckoPortfolio(String url) {
        Map<String, Coin> coins = new LinkedHashMap<>();
        Response response = null;
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error(response.message());
                throw new CoingeckoException();
            }
            assert response.body() != null;
            String responseBody = response.body().string();
            String[] coinsRaw = responseBody.split("<img loading=\"lazy\" alt=\"");
            for (int i = 1; i < coinsRaw.length - 2; i = i + 2) {
                Coin coin = CoinFactory.fromRawCoin(coinsRaw[i]);
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
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

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
}
