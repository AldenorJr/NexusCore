package br.com.nexus.main.core.database.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {

    protected static JedisPool jedisPool;

    public void openConnection(String andress, int port) {
        try {
            jedisPool = new JedisPool(andress, port);
        } catch (Exception ignored) {
        }
    }

    public Jedis getConnection() {
        return jedisPool.getResource();
    }

    public void closeConnection() {
        jedisPool.close();
    }

}
