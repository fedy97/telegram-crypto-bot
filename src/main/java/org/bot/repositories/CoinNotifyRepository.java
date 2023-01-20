package org.bot.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bot.models.CoinNotify;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.repositories.base.Repository;
import org.bot.utils.EnvVars;
import org.bot.utils.MongoConfig;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinNotifyRepository implements Repository<CoinNotify>, Observer<CoinNotify> {

    private static final String COLLECTION_NAME = "coin_notifiers";
    private static CoinNotifyRepository instance;
    private final MongoCollection<Document> coinNotifiers;

    private CoinNotifyRepository() {
        MongoDatabase database = MongoConfig.getInstance().getDatabase();
        this.coinNotifiers = database.getCollection(getCollectionName());
    }

    public static CoinNotifyRepository getInstance() {
        if (instance == null) {
            instance = new CoinNotifyRepository();
        }
        return instance;
    }

    @Override
    public String getCollectionName() {
        String collectionName = EnvVars.getEnvVar("COIN_NOTIFIERS_COLLECTION");
        return collectionName != null && collectionName.length() > 0 ? collectionName : COLLECTION_NAME;
    }

    @Override
    public void save(CoinNotify coin) {
        Document doc = new Document()
                .append("ticker", coin.getTicker())
                .append("chatId", coin.getChatId())
                .append("percentageChange", coin.getPercentageChange());
        coinNotifiers.insertOne(doc);
    }

    @Override
    public List<CoinNotify> findAll() {
        List<Document> docs;
        List<CoinNotify> result = new ArrayList<>();
        docs = coinNotifiers.find().into(new ArrayList<>());
        for (Document doc : docs) {
            CoinNotify coin = new CoinNotify();
            try {
                coin.setId(doc.getObjectId("_id").toString());
                coin.setTicker(doc.getString("ticker"));
                coin.setChatId(doc.getLong("chatId"));
                coin.setPercentageChange(doc.getInteger("percentageChange"));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            result.add(coin);
        }
        return result;
    }

    @Override
    public void deleteByValue(String column, String val) {
        if (column.equals("_id")) {
            deleteById(val);
            return;
        }
        Bson filter = Filters.eq(column, val);
        // Delete the document that matches the filter
        coinNotifiers.deleteOne(filter);
    }

    @Override
    public void deleteAll() {
        coinNotifiers.deleteMany(new Document());
    }

    public void deleteById(String id) {
        coinNotifiers.deleteOne(new Document("_id", new ObjectId(id)));
    }

    @Override
    public void update(Action<CoinNotify> action) {
        action.updateData(this);
    }
}
