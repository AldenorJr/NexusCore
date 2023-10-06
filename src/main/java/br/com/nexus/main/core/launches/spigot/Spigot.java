package br.com.nexus.main.core.launches.spigot;

import br.com.nexus.main.core.database.MongoDB.MongoDatabase;
import br.com.nexus.main.core.database.redis.RedisConnection;
import br.com.nexus.main.core.launches.spigot.listener.PlayerJoinServer;
import br.com.nexus.main.core.launches.spigot.listener.economy.EconomyCommand;
import br.com.nexus.main.core.launches.spigot.listener.economy.EventArmorStand;
import br.com.nexus.main.core.launches.spigot.listener.economy.RankingEvent;
import br.com.nexus.main.core.launches.spigot.redis.economy.Economy;
import br.com.nexus.main.core.launches.spigot.redis.economy.EconomyArmorStand;
import br.com.nexus.main.core.launches.spigot.redis.economy.EconomyRanking;
import br.com.nexus.main.core.launches.spigot.redis.server.ServerDisableRedis;
import br.com.nexus.main.core.launches.spigot.redis.server.ServerEnableRedis;
import br.com.nexus.main.core.launches.spigot.util.HologramUtil;
import br.com.nexus.main.core.launches.spigot.util.RankingUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    private final RedisConnection redisConnection = new RedisConnection();
    private final MongoDatabase mongoConnection = new MongoDatabase();
    private final ServerEnableRedis serverEnable = new ServerEnableRedis(redisConnection, this);
    private final Economy economy = new Economy(this);
    private final HologramUtil hologramUtil = new HologramUtil(this);
    private final PlayerJoinServer playerJoinServer = new PlayerJoinServer(economy);
    private final EventArmorStand eventArmorStand = new EventArmorStand();
    private final RankingUtil rankingUtil = new RankingUtil(this);
    private final EconomyArmorStand economyArmoStand = new EconomyArmorStand(mongoConnection, hologramUtil, rankingUtil);
    private final EconomyCommand economyCommand = new EconomyCommand(economy, economyArmoStand);
    private final ServerDisableRedis serverDisableRedis = new ServerDisableRedis(redisConnection, this);
    private final EconomyRanking economyRanking = new EconomyRanking(economy);
    private final RankingEvent rankingEvent = new RankingEvent();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerDatabase();
        registerServerBungeecord();
        loadRanking();

        getServer().getPluginManager().registerEvents(playerJoinServer, this);
        getServer().getPluginManager().registerEvents(economyCommand, this);
        getServer().getPluginManager().registerEvents(rankingEvent, this);
        getServer().getPluginManager().registerEvents(eventArmorStand, this);
        mongoConnection.mappingObject();

        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aHabilitado §c§lSPIGOT§a.");
    }

    public void registerServerBungeecord() {
        serverEnable.sendPublishMessage();
    }

    public void loadRanking() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                economyRanking.loadRanking();
                economyArmoStand.loadRankingArmorStand();
            }
        }, 0, 20 * 60 * 5);
    }

    public void registerDatabase() {
        redisConnection.openConnection(getConfig().getString("Redis.address"), getConfig().getInt("Redis.port"));
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aRedis conectado com sucesso.");
        mongoConnection.openConnection(getConfig().getString("MongoDB.address"), getConfig().getInt("MongoDB.port"));
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §aMongoDB conectado com sucesso.");
    }

    @Override
    public void onDisable() {
        serverDisableRedis.sendPublishMessage();
        Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cDisabilitando plugin.");
        hologramUtil.destroyAllEntity();
        try {
            mongoConnection.closeConnection();
            Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cMongoDB fechado com sucesso.");
            redisConnection.closeConnection();
            Bukkit.getConsoleSender().sendMessage("§6§l[NexusCore] §cRedis fechado com sucesso.");
        } catch (Exception ignored) {}
    }

    public Economy getEconomyRedis() {
        return economy;
    }

    public MongoDatabase getMongoConnection() {
        return mongoConnection;
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }
}
