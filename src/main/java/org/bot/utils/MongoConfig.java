package org.bot.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

public class MongoConfig {
    private static final String DEFAULT_URL = EnvVars.getEnvVar("MONGO_DB_URI", null);
    private static final String DEFAULT_DATABASE = "coins";

    private static MongoConfig instance;

    private MongoClient mongoClient;
    @Getter
    private MongoDatabase database;
    @Getter
    private boolean isMongoUp;

    private MongoConfig() {
        String url = System.getProperty("mongodb.url", DEFAULT_URL);
        String db = System.getProperty("mongodb.database", DEFAULT_DATABASE);
        try {
            this.mongoClient = MongoClients.create(url);
            this.database = mongoClient.getDatabase(db);
            this.isMongoUp = true;
        } catch (Exception e) {
            this.isMongoUp = false;
        }
    }

    public static MongoConfig getInstance() {
        if (instance == null) {
            instance = new MongoConfig();
        }
        return instance;
    }

    public static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.close();
            }
        }));
    }

    public void close() {
        mongoClient.close();
    }
}
