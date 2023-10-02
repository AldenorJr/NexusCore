package br.com.nexus.main.core;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.listener.ServerEnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    private final RedisConnection redisConnection = new RedisConnection();
    private final MongoConnection mongoConnection = new MongoConnection();
    private final ServerEnable serverEnable = new ServerEnable(redisConnection, this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        redisConnection.openConnection(getConfig().getString("Redis.address"), getConfig().getInt("Redis.port"));
        mongoConnection.openConnection(getConfig().getString("MongoDB.address"), getConfig().getInt("MongoDB.port"));
        serverEnable.sendPublishMessage();

        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aHabilitado §c§lSPIGOT §a.");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cDisabilitado.");
        redisConnection.closeConnection();
        mongoConnection.closeConnection();
    }


}
