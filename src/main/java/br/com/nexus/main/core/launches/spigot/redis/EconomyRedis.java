package br.com.nexus.main.core.launches.spigot.redis;

import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.spigot.Spigot;
import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class EconomyRedis {

    private final RedisConnection redisConnection;

    public EconomyRedis(Spigot spigot) {
        this.redisConnection = spigot.getRedisConnection();
    }

    public void registerPlayer(String player) {
        Jedis jedis = redisConnection.getConnection();
        String name = player;
        for (EconomyEnum value : EconomyEnum.values())
            if(jedis.hget(name, value.getEconomyName()) == null) jedis.hset(name, value.getEconomyName(), new BigInteger("0").toString());
        jedis.close();
    }

    public BigDecimal getBalance(String player, EconomyEnum economyEnum) {
        Jedis jedis = redisConnection.getConnection();
        String economyName = economyEnum.getEconomyName();
        BigDecimal bigDecimal = new BigDecimal(jedis.hget(player, economyName));
        jedis.close();
        return bigDecimal;
    }

    public void bankSetValue(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        Jedis jedis = redisConnection.getConnection();
        jedis.hset(player, economyEnum.getEconomyName(), bigDecimal.toPlainString());
        jedis.close();
    }

    public void bankDeposit(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        BigDecimal value = getBalance(player, economyEnum).add(bigDecimal);
        bankSetValue(player, economyEnum, value);
    }

    public void bankWithdrawn(String player, EconomyEnum economyEnum, BigDecimal bigDecimal) {
        BigDecimal value = getBalance(player, economyEnum).subtract(bigDecimal);
        bankSetValue(player, economyEnum, value);
    }

}
