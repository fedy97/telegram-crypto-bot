package org.bot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
        CloseableHttpResponse response;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            String[] coinsRaw = responseBody.split("<img loading=\"lazy\" alt=\"");
            for (int i = 1; i < coinsRaw.length; i = i + 2) {
                Coin coin = CoinFactory.fromRawCoin(coinsRaw[i]);
                coins.put(coin.getTicker().toUpperCase(), coin);
            }
        } catch (Exception e) {
            //log.error("Error in fetching resource");
            //throw new CoingeckoException();
        }
        return new Portfolio(coins);
    }

    public Trending getTrendingCoins() {
        String url = "https://api.coingecko.com/api/v3/search/trending";
        CloseableHttpResponse response;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            return new Trending(responseBody);
        } catch (Exception e) {
            log.error("Error in fetching resource");
            throw new CoingeckoException();
        }
    }
}
