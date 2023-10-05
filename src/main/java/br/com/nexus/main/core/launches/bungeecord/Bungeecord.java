package br.com.nexus.main.core.launches.bungeecord;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.MongoDB.method.UsersMethodDatabase;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.bungeecord.redis.ServerRegistration;
import br.com.nexus.main.core.launches.bungeecord.listener.DatabaseJoinProxiedPlayers;
import br.com.nexus.main.core.launches.bungeecord.util.ConfigurationFile;
import br.com.nexus.main.core.launches.bungeecord.util.TextComponentUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;

public class Bungeecord extends Plugin {

    private final TextComponentUtil textComponentUtil = new TextComponentUtil();
    private final ConfigurationFile configurationFile = new ConfigurationFile(this);
    private final RedisConnection redisConnection = new RedisConnection();
    private final MongoConnection mongoConnection = new MongoConnection();
    private final UsersMethodDatabase usersMethodDatabase = new UsersMethodDatabase(mongoConnection, textComponentUtil);
    private final DatabaseJoinProxiedPlayers databaseJoinProxiedPlayers = new DatabaseJoinProxiedPlayers(usersMethodDatabase);
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
