package org.bot.providers;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.HttpResponseException;
import org.bot.models.Coin;
import org.bot.models.Portfolio;
import org.bot.models.Trending;
import org.bot.utils.exceptions.CoinMarketCapException;
import org.bot.utils.exceptions.NotImplementedException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class CoinMarketCapProvider implements DataProvider {
    private static CoinMarketCapProvider instance;

    private CoinMarketCapProvider() {
        // Private constructor to prevent instantiation
    }

    public static CoinMarketCapProvider getInstance() {
        if (instance == null) {
            instance = new CoinMarketCapProvider();
        }
        return instance;
    }

    @Override
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
            String[] coinsRaw = responseBody.split("class=\"sc-4984dd93-0 kKpPOn\">");
            for (int i = 1; i < coinsRaw.length; i = i + 1) {
                Coin coin = this.fromRawCoin(coinsRaw[i]);
                if (coin.getTicker() != null) coins.put(coin.getTicker().toUpperCase(), coin);
            }
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            log.error(e.toString());
            throw new CoinMarketCapException();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new Portfolio(coins);
    }

    @Override
    public Trending getTrendingCoins() {
        throw new NotImplementedException();
    }

    @Override
    public Coin fromRawCoin(String raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.split("<")[0]);
        } catch (Exception e) {
            log.warn("name for token ignored: " + e);
        }
        try {
            coin.setTicker(raw.split("click=\"true\">")[1].split("<")[0]);
        } catch (Exception e) {
            log.warn("ticker for token ignored: " + e);
        }
        try {
            coin.setLink("https://coinmarketcap.com" + raw.split("<a href=\"")[1].split("\"")[0]);
        } catch (Exception e) {
            log.warn("link for token ignored: " + e);
        }
        try {
            coin.setPrice(Double.parseDouble(raw.split("\"cmc-link\"><span>\\$")[1].split("<")[0].replace(",", "")));
        } catch (Exception e) {
            log.warn("price for token ignored: " + e);
        }
        try {
            coin.setChange24(raw.split("display:inline-block\"></span>")[2].split("<")[0]);
        } catch (Exception e) {
            log.warn("change24 for token ignored: " + e);
        }
        return coin;
    }
}
