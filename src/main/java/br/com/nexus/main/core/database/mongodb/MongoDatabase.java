package br.com.nexus.main.core.database.mongodb;

import com.mongodb.MongoClient;

public class MongoDatabase {

    protected static MongoClient mongoClient;

    public void openConnection(String address, int port) {
        try {
            mongoClient = new MongoClient(address, port);
        } catch (Exception ignored) {
        }
    }

    public MongoClient getConnection() {
        return mongoClient;
    }

    public void closeConnection() {
        mongoClient.close();
    }

}
