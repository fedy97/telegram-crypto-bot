package org.bot.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bot.models.PortfolioLink;
import org.bot.observer.Observer;
import org.bot.observer.actions.Action;
import org.bot.repositories.base.Repository;
import org.bot.utils.EnvVars;
import org.bot.utils.MongoConfig;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PortfolioLinkRepository implements Repository<PortfolioLink>, Observer<PortfolioLink> {

    private static final String COLLECTION_NAME = "portfolio_links";
    private static PortfolioLinkRepository instance;
    private final MongoCollection<Document> links;

    private PortfolioLinkRepository() {
        MongoDatabase database = MongoConfig.getInstance().getDatabase();
        this.links = database.getCollection(getCollectionName());
    }

    public static PortfolioLinkRepository getInstance() {
        if (instance == null) {
            instance = new PortfolioLinkRepository();
        }
        return instance;
    }

    @Override
    public String getCollectionName() {
        String collectionName = EnvVars.getEnvVar("PORTFOLIO_LINKS_COLLECTION");
        return collectionName != null && collectionName.length() > 0 ? collectionName : COLLECTION_NAME;
    }

    @Override
    public void save(PortfolioLink link) {
        Document doc = new Document()
                .append("link", link.getLink())
                .append("name", link.getName());
        links.insertOne(doc);
    }

    @Override
    public List<PortfolioLink> findAll() {
        List<Document> docs;
        List<PortfolioLink> result = new ArrayList<>();
        docs = links.find().into(new ArrayList<>());
        for (Document doc : docs) {
            PortfolioLink link = new PortfolioLink();
            link.setLink(doc.getString("link"));
            link.setName(doc.getString("name"));
            result.add(link);
        }
        return result;
    }

    @Override
    public void deleteByValue(String column, String val) {
        Bson filter = Filters.eq(column, val);
        // Delete the document that matches the filter
        links.deleteOne(filter);
    }

    @Override
    public void deleteAll() {
        links.deleteMany(new Document());
    }

    @Override
    public void update(Action<PortfolioLink> action) {
        action.updateData(this);
    }
}
