package br.com.nexus.main.core.models;

import br.com.nexus.main.core.DTO.VipsDTO;
import br.com.nexus.main.core.database.MongoDB.MongoConnection;
import br.com.nexus.main.core.database.MongoDB.method.VipsMethodDatabase;

public class PlayerVipModel {

    private String ID;
    private Long timeExpire;
    private final MongoConnection mongoConnection;

    public PlayerVipModel(String ID, Long timeExpire, MongoConnection mongoConnection) {
        this.ID = ID;
        this.timeExpire = timeExpire;
        this.mongoConnection = mongoConnection;
    }

    public VipsDTO getVip() {
        return new VipsMethodDatabase(mongoConnection).getVIP(getID());
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Long getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Long timeExpire) {
        this.timeExpire = timeExpire;
    }
}
