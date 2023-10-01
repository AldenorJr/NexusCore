package br.com.nexus.main.core.api;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.redis.RedisConnection;
import com.mongodb.MongoClient;
import redis.clients.jedis.Jedis;

public class ConnectionAPI {

    public Jedis getConnectionRedis() {
        return new RedisConnection().getConnection();
    }

    public MongoClient getConnectionMongo() {
        return new MongoConnection().getConnection();
    }

}
