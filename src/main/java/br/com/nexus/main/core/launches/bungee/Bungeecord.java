package br.com.nexus.main.core.launches.bungee;

import br.com.nexus.main.core.database.mongodb.MongoDatabase;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.bungee.redis.ServerRegistration;
import br.com.nexus.main.core.launches.bungee.listener.ProxiedPlayersJoin;
import br.com.nexus.main.core.launches.bungee.util.ConfigurationFile;
import br.com.nexus.main.core.launches.bungee.util.TextComponentUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;

public class Bungeecord extends Plugin {

    private final TextComponentUtil textComponentUtil = new TextComponentUtil();
    private final ConfigurationFile configurationFile = new ConfigurationFile(this);
    private final RedisConnection redisConnection = new RedisConnection();
    private final MongoDatabase mongoConnection = new MongoDatabase();
    private final ProxiedPlayersJoin databaseJoinProxiedPlayers = new ProxiedPlayersJoin(mongoConnection);
    private final ServerRegistration serverRegistration = new ServerRegistration(redisConnection, textComponentUtil);

    @Override
    public void onEnable() {
        try {
            configurationFile.loadConfig();
            Configuration config = configurationFile.getConfig();

            redisConnection.openConnection(config.getString("Redis.address"), config.getInt("Redis.port"));
            mongoConnection.openConnection(config.getString("MongoDB.address"), config.getInt("MongoDB.port"));
        } catch (IOException e) {
            BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent("§6§l[NexusCore] §aErro ao carregar a config.yml"));
        }
        BungeeCord.getInstance().getPluginManager().registerListener(this, databaseJoinProxiedPlayers);
        serverRegistration.sendSubscriberMessage();
        BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent("§6§l[NexusCore] §aHabilitado §c§lBUNGEECORD."));
    }

    @Override
    public void onDisable() {
        redisConnection.closeConnection();
        mongoConnection.closeConnection();
        BungeeCord.getInstance().getConsole().sendMessage((textComponentUtil.createTextComponent("§6§l[NexusCore] §cDesabilitado")));
    }
}
