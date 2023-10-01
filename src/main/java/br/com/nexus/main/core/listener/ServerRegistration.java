package br.com.nexus.main.core.listener;

import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.util.TextComponentUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRegistration {

    private final RedisConnection redisConnection;
    private final TextComponentUtil textComponentUtil;

    public ServerRegistration(RedisConnection redisConnection, TextComponentUtil textComponentUtil) {
        this.redisConnection = redisConnection;
        this.textComponentUtil = textComponentUtil;
    }

    public void sendSubscriberMessage() {
        Jedis jedis = redisConnection.getConnection();
        String channel = "Bungeecord";
        JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                String[] args = message.split(" ");
                BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent("§6§l[NexusCore] §aRegistrando o servidor, " + args[4] + "."));
                if(args[0].equalsIgnoreCase("REGISTER_SERVER")) {
                    String name = args[4];
                    String address = args[2];
                    String port = args[1];
                    String motd = args[3];
                    ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, InetSocketAddress.createUnresolved(address, Integer.parseInt(port)), motd, false);
                    ProxyServer.getInstance().getServers().put(name, serverInfo);
                }
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> jedis.subscribe(jedisPubSub, channel));
    }
}
