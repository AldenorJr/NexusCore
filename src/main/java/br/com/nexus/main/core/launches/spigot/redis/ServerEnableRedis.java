package br.com.nexus.main.core.launches.spigot.listener;

import br.com.nexus.main.core.launches.spigot.Spigot;
import br.com.nexus.main.core.database.redis.RedisConnection;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

public class ServerEnable {

    private final RedisConnection redisConnection;
    private final Spigot main;

    public ServerEnable(RedisConnection redisConnection, Spigot main) {
        this.redisConnection = redisConnection;
        this.main = main;
    }

    public void sendPublishMessage() {
        String channel = "Bungeecord";
        Jedis jedis = redisConnection.getConnection();

        String motd = main.getConfig().getString("Server-info.motd");
        String address = main.getConfig().getString("Server-info.address");
        String port = main.getConfig().getString("Server-info.port");
        String name = main.getConfig().getString("Server-info.name");

        jedis.publish(channel, "REGISTER_SERVER " + port + " " + address + " " + motd + " " + name);
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aEnviado solicitação de registro do servidor "+name+".");
        jedis.close();
    }

}
