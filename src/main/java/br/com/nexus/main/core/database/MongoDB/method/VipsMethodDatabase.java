package br.com.nexus.main.core.database.MongoDB.method;

import br.com.nexus.main.core.DTO.VipsDTO;
import br.com.nexus.main.core.database.MongoDB.MongoConnection;

public class VipsMethodDatabase {

    private final MongoConnection mongoClient;

    public VipsMethodDatabase(MongoConnection mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void createVIPs(VipsDTO vipsDTO) {

    }

    public VipsDTO getVIP(String id) {

    }

}
