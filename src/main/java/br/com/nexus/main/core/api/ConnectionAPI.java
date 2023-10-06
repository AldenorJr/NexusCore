package br.com.nexus.main.core.api;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.spigot.Spigot;
import com.mongodb.MongoClient;
import redis.clients.jedis.Jedis;

public class ConnectionAPI {

    private final RedisConnection redisConnection;
    private final MongoConnection mongoConnection;

    public ConnectionAPI() {
        Spigot plugin = Spigot.getPlugin(Spigot.class);
        this.redisConnection = plugin.getRedisConnection();
        this.mongoConnection = plugin.getMongoConnection();
    }

    public Jedis getConnectionRedis() {
        return redisConnection.getConnection();
    }

    public MongoClient getConnectionMongo() {
        return mongoConnection.getConnection();
    }

}
