package br.com.nexus.main.core.listener.bungeecord;


import br.com.nexus.main.core.database.MongoDB.method.UsersMethodDatabase;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DatabaseJoinProxiedPlayers implements Listener {

    private final UsersMethodDatabase usersMethodDatabase;

    public DatabaseJoinProxiedPlayers(UsersMethodDatabase usersMethodDatabase) {
        this.usersMethodDatabase = usersMethodDatabase;
    }

    @EventHandler
    public void onJoinEvent(PostLoginEvent e) {
        usersMethodDatabase.createDatabase(e.getPlayer());
    }

}
