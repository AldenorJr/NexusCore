package br.com.nexus.main.core.database.MongoDB.method;

import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.models.PlayerPetsModel;
import br.com.nexus.main.core.models.PlayerSymbolModel;
import br.com.nexus.main.core.models.PlayerTagsModel;
import br.com.nexus.main.core.models.PlayerVipModel;
import br.com.nexus.main.core.util.TextComponentUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

public class UsersMethodDatabase {

    private final MongoConnection mongoClient;
    private final TextComponentUtil textComponentUtil;

    public UsersMethodDatabase(MongoConnection mongoClient, TextComponentUtil textComponentUtil) {
        this.mongoClient = mongoClient;
        this.textComponentUtil = textComponentUtil;
    }

    public void createDatabase(ProxiedPlayer player) {
        MongoDatabase nexusUsers = mongoClient.getConnection().getDatabase("RedeCrystal");
        MongoCollection<Document> users = nexusUsers.getCollection("Users");
        Document user = users.find(new Document("Nick", player.getName())).first();
        if (user == null) {
            Document newPlayer = new Document("Nick", player.getName());
            newPlayer.append("FirstJoin", new Date())
                    .append("LastJoin", new Date())
                    .append("VIPs", new ArrayList<PlayerVipModel>())
                    .append("TAGs", new ArrayList<PlayerTagsModel>())
                    .append("Symbol", new ArrayList<PlayerSymbolModel>())
                    .append("Pets", new ArrayList<PlayerPetsModel>())
                    .append("Member", false);
            users.insertOne(newPlayer);
            BungeeCord.getInstance().getConsole().sendMessage(textComponentUtil.createTextComponent("§6§l[NexusCore] §aJogador " + player.getName() + " foi criado no banco de dados."));
        } else {
            Document lastJoin = new Document("LastJoin", new Date());
            Document updated = new Document("$set", lastJoin);
            users.updateOne(user, updated);
        }
    }

    public void loadDocuments() {

    }

}
