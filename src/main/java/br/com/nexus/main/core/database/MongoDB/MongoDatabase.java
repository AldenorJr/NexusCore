package br.com.nexus.main.core.database.MongoDB;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDatabase {

    protected static MongoClient mongoClient;
    protected static Datastore datastore;

    public void openConnection(String address, int port) {
        try {
            mongoClient = new MongoClient(address, port);
        } catch (Exception ignored) {
        }
    }

    public void mappingObject() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("br.com.nexus.main.core.object");
        datastore = morphia.createDatastore(mongoClient, "NexusCore");
        datastore.ensureIndexes();
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public MongoClient getConnection() {
        return mongoClient;
    }

    public void closeConnection() {
        mongoClient.close();
    }

}
