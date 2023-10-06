package br.com.nexus.main.core.launches.bungee.util;

import java.io.File;
import java.io.IOException;

import br.com.nexus.main.core.launches.bungee.Bungeecord;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigurationFile {

    private final Bungeecord bungeecord;

    public ConfigurationFile(Bungeecord bungeecord) {
        this.bungeecord = bungeecord;
    }
    public void loadConfig() throws IOException {
        File file = getFile();
        if(!hasFile(file)) createFile(file);
        Configuration configuration = getConfigurationByFile(file);
        setDefaultConfiguration(configuration);
        saveConfig(file, configuration);
    }

    private void setDefaultConfiguration(Configuration configuration) {
        if(!configuration.contains("Redis")) setDefaultRedis(configuration);
        if(!configuration.contains("MongoDB")) setDefaultMongoDB(configuration);
        if(!configuration.contains("Token")) setDefaultToken(configuration);
    }

    private void setDefaultToken(Configuration configuration) {
        configuration.set("Token", "ASIJDAASIDU43UASSDJASOPA@!#@ODSB");
    }

    private void setDefaultRedis(Configuration configuration) {
        configuration.set("Redis.address", "127.0.0.1");
        configuration.set("Redis.port", 6379);
    }

    private void setDefaultMongoDB(Configuration configuration) {
        configuration.set("MongoDB.address", "127.0.0.1");
        configuration.set("MongoDB.port", 27017);
    }

    public Configuration getConfig() throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile());
    }

    public void saveConfig(Configuration configuration) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, getFile());
    }

    private void saveConfig(File file, Configuration configuration) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
    }

    private File getFile() {
        if(!hasDataFolder()) createDataFolder();
        return new File(bungeecord.getDataFolder(), "configuration.yml");
    }

    private boolean hasDataFolder() {
        return bungeecord.getDataFolder().exists();
    }

    private void createDataFolder() {
        bungeecord.getDataFolder().mkdir();
    }

    private Configuration getConfigurationByFile(File file) throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    private boolean hasFile(File file) {
        return file.exists();
    }

    private void createFile(File file) throws IOException {
        file.createNewFile();
    }
}
