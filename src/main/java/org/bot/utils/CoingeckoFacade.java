package org.bot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bot.models.Coin;
import org.bot.models.Portfolio;

import java.util.ArrayList;
import java.util.List;

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
        List<Coin> coins = new ArrayList<>();
        CloseableHttpResponse response;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            String[] coinsRaw = responseBody.split("<img class=\"lazy\" alt=\"");
            for (int i = 1; i < coinsRaw.length; i = i + 2)
                coins.add(Coin.fromRawCoin(coinsRaw[i]));
        } catch (Exception e) {
            log.error("Error in fetching resource");
        }
        return new Portfolio(coins);
    }


}
