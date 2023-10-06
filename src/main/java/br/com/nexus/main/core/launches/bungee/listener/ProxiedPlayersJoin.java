package br.com.nexus.main.core.launches.bungee.listener;


import br.com.nexus.main.core.database.MongoDB.method.UsersMethodDatabase;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxiedPlayersJoin implements Listener {

    private final UsersMethodDatabase usersMethodDatabase;

    public ProxiedPlayersJoin(UsersMethodDatabase usersMethodDatabase) {
        this.usersMethodDatabase = usersMethodDatabase;
    }

    @EventHandler
    public void onJoinEvent(PostLoginEvent e) {
        usersMethodDatabase.insertDatabase(e.getPlayer());
    }

    @EventHandler
    public void onPriorityEvent(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ArrayList<ServerInfo> servers = new ArrayList<>();
        try {
            for(String name : BungeeCord.getInstance().getServers().keySet()) if(name.contains("-priority")) servers.add(BungeeCord.getInstance().getServers().get(name));
            if(servers.isEmpty()) {
                player.disconnect(new TextComponent(TextComponent.fromLegacyText("§6§l[NexusCore] §cNão há servidores disponíveis no momento, tente novamente mais tarde.")));
                return;
            }
            HashMap<ServerInfo, Integer> serverHash = new HashMap<>();
            for(ServerInfo serverInfo : servers) {
                int players = serverInfo.getPlayers().size();
                serverHash.put(serverInfo, players);
            }
            List<Map.Entry<ServerInfo, Integer>> list = new ArrayList<>(serverHash.entrySet());
            list.sort(Map.Entry.comparingByValue());
            ServerInfo serverInfo = list.get(0).getKey();
            event.setTarget(serverInfo);
        } catch (Exception ignored) {}
    }

}
