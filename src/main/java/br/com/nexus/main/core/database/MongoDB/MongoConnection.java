package br.com.nexus.main.core.database.MongoDB;

import com.mongodb.MongoClient;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPool;

public class MongoConnection {

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
