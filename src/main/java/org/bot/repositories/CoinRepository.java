package org.bot.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bot.models.Coin;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.utils.MongoConfig;
import org.bot.utils.Utils;
import org.bot.utils.fetchers.DataFetcher;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinRepository implements Repository<Coin>, DataFetcher<Coin>, Observer<Coin> {

    private final MongoCollection<Document> coins;

    private static CoinRepository instance;
    public static CoinRepository getInstance() {
        if (instance == null) {
            instance = new CoinRepository();
        }
        return instance;
    }

    private CoinRepository(){
        MongoDatabase database = MongoConfig.getInstance().getDatabase();
        this.coins = database.getCollection(getCollectionName());
    }


    @Override
    public String getCollectionName() {
        return Utils.getEnvVar("COINS_COLLECTION");
    }

    @Override
    public void save(Coin coin) {
        Document doc = new Document()
                .append("ticker", coin.getTicker())
                .append("price", coin.getPrice());
        coins.insertOne(doc);
    }

    @Override
    public List<Coin> findAll() {
        List<Document> docs;
        List<Coin> result = new ArrayList<>();
        docs = coins.find().into(new ArrayList<>());
        for (Document doc : docs) {
            Coin coin = new Coin();
            coin.setTicker(doc.getString("ticker"));
            coin.setPrice(doc.getDouble("price"));
            result.add(coin);
        }
        return result;
    }

    @Override
    public void deleteByValue(String column, String val) {
        Bson filter = Filters.eq(column, val);
        // Delete the document that matches the filter
        coins.deleteOne(filter);
    }

    @Override
    public void deleteAll() {
        coins.deleteMany(new Document());
    }

    @Override
    public List<Coin> fetchAll() {
        return findAll();
    }

    @Override
    public void update(Action<Coin> action) {
        action.executedBy(this);
    }
}
