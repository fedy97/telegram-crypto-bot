package org.bot.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bot.models.Coin;
import org.bot.utils.MongoConfig;
import org.bot.utils.QueryCache;
import org.bot.utils.Utils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoCoinRepository implements Repository<Coin> {

    private final MongoCollection<Document> coins;

    private static final String COLLECTION_NAME = Utils.getEnvVar("COINS_COLLECTION");

    private static MongoCoinRepository instance;
    public static MongoCoinRepository getInstance() {
        if (instance == null) {
            instance = new MongoCoinRepository();
        }
        return instance;
    }

    private MongoCoinRepository(){
        MongoDatabase database = MongoConfig.getInstance().getDatabase();
        this.coins = database.getCollection(COLLECTION_NAME);
    }


    @Override
    public void save(Coin coin) {
        Document doc = new Document()
                .append("ticker", coin.getTicker())
                .append("price", coin.getPrice());
        coins.insertOne(doc);
        QueryCache.getInstance().addElementToListCached(COLLECTION_NAME, doc);
    }

    @Override
    public List<Coin> findAll() {
        QueryCache cache = QueryCache.getInstance();
        List<Document> chachedDocs;
        List<Coin> result = new ArrayList<>();
        if (!cache.isCached(COLLECTION_NAME)) {
            chachedDocs = coins.find().into(new ArrayList<>());
            cache.addToCache(COLLECTION_NAME, chachedDocs);
        } else {
            chachedDocs = cache.getCachedResult(COLLECTION_NAME);
        }
        for (Document doc : chachedDocs) {
            Coin coin = new Coin();
            coin.setTicker(doc.getString("ticker"));
            coin.setPrice(doc.getDouble("price"));
            result.add(coin);
        }
        return result;
    }

    @Override
    public void deleteByValue(String column, String val) {
        QueryCache.getInstance().deleteElementToListCached(COLLECTION_NAME, column, val);
        Bson filter = Filters.eq(column, val);
        // Delete the document that matches the filter
        coins.deleteOne(filter);
    }

    @Override
    public void deleteAll() {
        QueryCache.getInstance().invalidateCache(COLLECTION_NAME);
        coins.deleteMany(new Document());
    }
}
