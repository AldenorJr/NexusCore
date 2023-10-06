package br.com.nexus.main.core.launches.spigot.redis.economy;

import br.com.nexus.main.core.database.MongoDB.MongoDatabase;
import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import br.com.nexus.main.core.launches.spigot.util.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EconomyArmorStand {

    private final MongoDatabase mongoConnection;
    private final HologramUtil hologramUtil;
    private final RankingUtil rankingUtil;

    public EconomyArmorStand(MongoDatabase mongoConnection, HologramUtil hologramUtil, RankingUtil rankingUtil) {
        this.mongoConnection = mongoConnection;
        this.hologramUtil = hologramUtil;
        this.rankingUtil = rankingUtil;
    }

    public void setDatabaseRanking(Player player, int position, EconomyEnum economyEnum) {
        MongoClient connection = mongoConnection.getConnection();
        MongoCollection<Document> collection = connection.getDatabase("RedeCrystal").getCollection("Ranking_economy");
        Document document = collection.find(new Document("position", position).append("economy", economyEnum.name())).first();
        if (document == null) {
            Document newRanking = new Document("position", position);
            newRanking.append("economy", economyEnum.name());
            newRanking.append("location", LocationUtil.serializeLoc(player.getLocation()));
            collection.insertOne(newRanking);
        } else {
            Document money = new Document("location", LocationUtil.serializeLoc(player.getLocation()));
            Document updated = new Document("$set", money);
            collection.updateOne(document, updated);
        }
        loadRankingArmorStand();
    }

    public void delDatabaseRanking(int position, EconomyEnum economyEnum) {
        MongoClient connection = mongoConnection.getConnection();
        MongoCollection<Document> collection = connection.getDatabase("RedeCrystal").getCollection("Ranking_economy");
        Document document = collection.find(new Document("position", position).append("economy", economyEnum.name())).first();
        if(document == null) return;
        else collection.deleteOne(document);
        loadRankingArmorStand();
    }

    public void loadRankingArmorStand() {
        MongoClient connection = mongoConnection.getConnection();
        MongoCollection<Document> collection = connection.getDatabase("RedeCrystal").getCollection("Ranking_economy");
        hologramUtil.destroyAllEntity();
        for (Document document : collection.find()) {
            EconomyEnum economyEnum = EconomyEnum.valueOf(document.getString("economy"));
            Location location = LocationUtil.desarializeLoc(document.getString("location"));
            int position = document.getInteger("position");
            String name = "yNexusxz";
            boolean hasPlayer = EconomyRanking.playersRanking.get(economyEnum).size() >= position;
            BigDecimal balance = null;
            if(hasPlayer) {
                name = new ArrayList<>(EconomyRanking.playersRanking.get(economyEnum).keySet()).get(position - 1);
                balance = new BigDecimal(EconomyRanking.playersRanking.get(economyEnum).get(name).toString());
            }
            switch (position) {
                case (1):
                    rankingUtil.setRanking(location,
                            new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(name).toItemStack(),
                            new ItemStack(Material.DIAMOND_CHESTPLATE),
                            new ItemStack(Material.DIAMOND_LEGGINGS),
                            new ItemStack(Material.DIAMOND_BOOTS));
                    break;
                case (2):
                    rankingUtil.setRanking(location,
                            new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(name).toItemStack(),
                            new ItemStack(Material.GOLD_CHESTPLATE),
                            new ItemStack(Material.GOLD_LEGGINGS),
                            new ItemStack(Material.GOLD_BOOTS));
                    break;
                case (3):
                    rankingUtil.setRanking(location,
                            new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(name).toItemStack(),
                            new ItemStack(Material.IRON_CHESTPLATE),
                            new ItemStack(Material.IRON_LEGGINGS),
                            new ItemStack(Material.IRON_BOOTS));
                    break;
                case (4):
                    rankingUtil.setRanking(location,
                            new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(name).toItemStack(),
                            new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                            new ItemStack(Material.CHAINMAIL_LEGGINGS),
                            new ItemStack(Material.CHAINMAIL_BOOTS));
                    break;
                default:
                    rankingUtil.setRanking(location,
                            new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setSkullOwner(name).toItemStack(),
                            new ItemStack(Material.LEATHER_CHESTPLATE),
                            new ItemStack(Material.LEATHER_LEGGINGS),
                            new ItemStack(Material.LEATHER_BOOTS));
                    break;
            }
            if(!hasPlayer) {
                hologramUtil.spawnHologram(location.add(0, 0.3,0), "§cNinguém.");
                hologramUtil.spawnHologram(location.add(0, 0.3, 0), "&f"+position+"º Lugar");
            } else {
                hologramUtil.spawnHologram(location.add(0, 0.3,0), economyEnum.getEconomyColor()+FormattedBigDecimal.formatarBigInteger(balance));
                hologramUtil.spawnHologram(location.add(0, 0.3,0), "§7"+name);
                hologramUtil.spawnHologram(location.add(0, 0.3,0), economyEnum.getEconomyColor()+"§l"+position+"# §fLugar");
            }
        }
    }

}
