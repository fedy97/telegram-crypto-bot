package org.bot.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {
    private static final String DEFAULT_URL = EnvVars.getEnvVar("MONGO_DB_URI");
    private static final String DEFAULT_DATABASE = "coins";

    private static MongoConfig instance;

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private MongoConfig() {
        String url = System.getProperty("mongodb.url", DEFAULT_URL);
        String db = System.getProperty("mongodb.database", DEFAULT_DATABASE);
        mongoClient = MongoClients.create(url);
        this.database = mongoClient.getDatabase(db);
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

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        mongoClient.close();
    }
}
