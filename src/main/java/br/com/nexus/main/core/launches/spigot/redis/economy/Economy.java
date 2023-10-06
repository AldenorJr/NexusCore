package br.com.nexus.main.core.launches.spigot.redis.economy;

import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.spigot.Spigot;
import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import org.bukkit.inventory.ItemStack;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Economy {

    private final RedisConnection redisConnection;

    public Economy(Spigot spigot) {
        this.redisConnection = spigot.getRedisConnection();
    }

    public boolean hasAccount(String player, EconomyEnum economyEnum) {
        Jedis jedis = redisConnection.getConnection();
        if(jedis.hexists(player, economyEnum.getEconomyName())) {
            jedis.close();
            return true;
        }
        jedis.close();
        return false;
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

    public Map<String, BigDecimal> getRankingEconomy(EconomyEnum economyEnum) {
        Jedis jedis = redisConnection.getConnection();
        String economyName = economyEnum.getEconomyName();
        ScanParams scanParams = new ScanParams().count(100);
        String cur = redis.clients.jedis.ScanParams.SCAN_POINTER_START;

        Map<String, BigDecimal> rankings = new HashMap<>();
        EconomyRanking.economyAll.put(economyEnum, new BigDecimal("50000000"));

        do {
            ScanResult<String> scanResult = jedis.scan(cur, scanParams);
            List<String> keys = scanResult.getResult();

            for (String key : keys) {
                if (jedis.hexists(key, economyName)) {
                    BigDecimal value = new BigDecimal(jedis.hget(key, economyName));
                    EconomyRanking.economyAll.put(economyEnum, EconomyRanking.economyAll.get(economyEnum).add(value));
                    rankings.put(key, value);
                }
            }
            cur = scanResult.getCursor();
        } while (!cur.equals(redis.clients.jedis.ScanParams.SCAN_POINTER_START));

        LinkedHashMap<String, BigDecimal> sortedRankings = rankings.entrySet()
                .stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        jedis.close();
        return sortedRankings;
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
