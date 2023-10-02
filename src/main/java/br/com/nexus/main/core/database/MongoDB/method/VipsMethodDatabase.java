package br.com.nexus.main.core.database.MongoDB.method;

import br.com.nexus.main.core.DTO.VipsDTO;
import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.models.PlayerPetsModel;
import br.com.nexus.main.core.models.PlayerSymbolModel;
import br.com.nexus.main.core.models.PlayerTagsModel;
import br.com.nexus.main.core.models.PlayerVipModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.md_5.bungee.BungeeCord;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

public class VipsMethodDatabase {

    private final MongoConnection mongoConnection;

    public VipsMethodDatabase(MongoConnection mongoClient) {
        this.mongoConnection = mongoClient;
    }

    public void insertDatabase(VipsDTO vipsDTO) {
        MongoDatabase nexusUsers = mongoConnection.getConnection().getDatabase("RedeCrystal");
        MongoCollection<Document> vips = nexusUsers.getCollection("Vips");
        Document vip = vips.find(new Document("Name", vipsDTO.getName())).first();
        if (vip == null) {
            Document newVip = new Document("Name", vipsDTO.getName());
            newVip.append("Color", vipsDTO.getColor())
                    .append("Prefix", new Document("Prefix", vipsDTO.getPrefix().replaceAll("&", "§")))
                    .append("Priority", vipsDTO.getPriority());
            vips.insertOne(newVip);
        } else {
            ArrayList<Document> updated = new ArrayList<>();
            updated.add(new Document("$set", new Document("Color", vipsDTO.getColor())));
            updated.add(new Document("$set", new Document("Prefix", vipsDTO.getPrefix().replaceAll("&", "§"))));
            updated.add(new Document("$set", new Document("Priority", vipsDTO.getPriority())));
            vips.updateOne(vip, updated);
        }
    }

    public VipsDTO getVIP(String id) {
        MongoDatabase nexusUsers = mongoConnection.getConnection().getDatabase("RedeCrystal");
        MongoCollection<Document> vips = nexusUsers.getCollection("Vips");
        Document vip = vips.find(new Document("ID", id)).first();
        if(vip != null) return new VipsDTO(vip.getString("Name"), vip.getString("Prefix"), vip.getString("Color"), vip.getString("Priority"));
        return null;
    }

}
