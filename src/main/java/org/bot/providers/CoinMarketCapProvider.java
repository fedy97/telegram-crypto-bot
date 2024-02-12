package org.bot.providers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    private CoinMarketCapProvider() {
        // Private constructor to prevent instantiation
        this.objectMapper = new ObjectMapper();
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
            String requestUrl = "https://api.coinmarketcap.com/asset/v3/watchlist/query";
            String requestBody = "{\n" +
                    "    \"watchListType\": \"FOLLOWED\",\n" +
                    "    \"aux\": 1,\n" +
                    "    \"watchListId\": \"" + url.split("watchlist/")[1].replace("/", "") + "\",\n" +
                    "    \"cryptoAux\": \"ath,atl,high24h,low24h,max_supply,self_reported_circulating_supply,self_reported_market_cap,circulating_supply,total_supply,volume_7d,volume_30d,tvl,audit,cmc_rank\",\n" +
                    "    \"convertIds\": \"2781\"\n" +
                    "}";
            Request request = this.buildPostRequest(requestUrl, requestBody);
            response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new HttpResponseException(response.code(), response.toString());
            assert response.body() != null;
            JsonNode jsonNode = this.objectMapper.readTree(response.body().string());
            JsonNode cryptoCurrencies = jsonNode.at("/data/watchLists/0/cryptoCurrencies");
            for (JsonNode coinNode : cryptoCurrencies) {
                Coin coin = this.fromRawCoin(coinNode);
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
    public Coin fromRawCoin(JsonNode raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.at("/name").asText());
            coin.setTicker(raw.at("/symbol").asText());
            coin.setLink("https://coinmarketcap.com/currencies/" + raw.at("/slug").asText());
            coin.setPrice(raw.at("/quotes/0/price").asDouble());
            double percentChange24h = raw.at("/quotes/0/percentChange24h").asDouble();
            coin.setChange24(Double.parseDouble(String.format("%.2f", percentChange24h)) + "%");
        } catch (Exception e) {
            log.warn("token ignored: " + e);
        }
        return coin;
    }
}
