package br.com.nexus.main.core.launches.spigot;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.spigot.listener.PlayerJoinServer;
import br.com.nexus.main.core.launches.spigot.listener.economy.EconomyCommand;
import br.com.nexus.main.core.launches.spigot.redis.EconomyRedis;
import br.com.nexus.main.core.launches.spigot.redis.ServerEnableRedis;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    private final RedisConnection redisConnection = new RedisConnection();
    private final MongoConnection mongoConnection = new MongoConnection();
    private final ServerEnableRedis serverEnable = new ServerEnableRedis(redisConnection, this);
    private final EconomyRedis economyRedis = new EconomyRedis(this);
    private final PlayerJoinServer playerJoinServer = new PlayerJoinServer(economyRedis);
    private final EconomyCommand economyCommand = new EconomyCommand(economyRedis);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerDatabase();
        registerServerBungeecord();
        getServer().getPluginManager().registerEvents(playerJoinServer, this);
        getServer().getPluginManager().registerEvents(economyCommand, this);
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aHabilitado §c§lSPIGOT §a.");

    }

    public void registerServerBungeecord() {
        serverEnable.sendPublishMessage();
    }

    public void registerDatabase() {
        redisConnection.openConnection(getConfig().getString("Redis.address"), getConfig().getInt("Redis.port"));
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aRedis conectado com sucesso.");
        mongoConnection.openConnection(getConfig().getString("MongoDB.address"), getConfig().getInt("MongoDB.port"));
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aMongoDB conectado com sucesso.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cDisabilitado.");
        redisConnection.closeConnection();
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cRedis fechado com sucesso.");
        mongoConnection.closeConnection();
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cMongoDB fechado com sucesso.");
    }

    public EconomyRedis getEconomyRedis() {
        return economyRedis;
    }

    public MongoConnection getMongoConnection() {
        return mongoConnection;
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }
}
