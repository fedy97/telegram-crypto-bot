package org.bot.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.bot.utils.formatters.ToBoldDecorator;
import org.bot.utils.formatters.ToItalicDecorator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Trending {

    List<Coin> coins;


    public Trending(String response) throws JsonProcessingException {
        this.coins = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(response);
        for (JsonNode jsonNode : jsonObject.get("coins")) {
            Coin coin = new Coin();
            coin.setTicker(jsonNode.get("item").get("symbol").asText());
            coin.setCoinName(jsonNode.get("item").get("name").asText());
            coins.add(coin);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Coingecko Trending coins:\n");
        for (Coin coin : coins) {
            res.append(new ToBoldDecorator(coin.getTicker().toUpperCase()));
            res.append(" - ");
            res.append(new ToItalicDecorator(coin.getCoinName()));
            res.append("\n");
        }
        return res.toString();
    }
}